package com.hui.huiapigateway;/**
 * 作者:灰爪哇
 * 时间:2023-03-29
 */

import com.hui.huiapiclientsdk.utils.SignUtils;
import com.quanhui.huiapicommon.model.entity.InterfaceInfo;
import com.quanhui.huiapicommon.model.entity.User;
import com.quanhui.huiapicommon.service.InnerInterfaceInfoService;
import com.quanhui.huiapicommon.service.InnerUserInterfaceInfoService;
import com.quanhui.huiapicommon.service.InnerUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.reactivestreams.Publisher;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 *
 * @author: shuaihui
 **/
@Slf4j
@Component
public class CustomGlobalFilter implements GlobalFilter, Ordered {

    @DubboReference
    private InnerUserService innerUserService;

    @DubboReference
    private InnerInterfaceInfoService innerInterfaceInfoService;

    @DubboReference
    private InnerUserInterfaceInfoService innerUserInterfaceInfoService;

    private static final List<String> IP_WHITE_LIST = Arrays.asList("127.0.0.1");

    private static final String INTERFACE_HOST = "http://localhost:8123";


    //请求白名单

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("custom global filter");

//        1. 请求日志
        ServerHttpRequest request = exchange.getRequest();
        String path = INTERFACE_HOST + request.getPath().value();
        String method = request.getMethod().toString();
        log.info("请求唯一标识 "+request.getId());
        log.info("请求路径 "+request.getPath().value());
        log.info("请求方法 "+request.getMethod());
        log.info("请求参数 "+request.getQueryParams());
        log.info("请求来源地址 "+request.getRemoteAddress());

        String sourceAddress = request.getLocalAddress().getHostString();

        ServerHttpResponse response = exchange.getResponse();

//        2. （黑白名单）
        if (!IP_WHITE_LIST.contains(sourceAddress)){
            handleNoAuth(response);
        }
//        3. 用户鉴权（判断 ak、sk 是否合法）
        //从请求头中取出数据
        HttpHeaders headers = request.getHeaders();
        String accessKey = headers.getFirst("accessKey");
        String nonce = headers.getFirst("nonce");
        String timestamp = headers.getFirst("timestamp");
        String sign = headers.getFirst("sign");
        String body = headers.getFirst("body");

        // 实际上是从数据库查看是否已分配用户
        User invokeUser = null;
        try {
            invokeUser = innerUserService.getInvokeUser(accessKey);
        } catch (Exception e) {
            log.error("getInvokeUser error", e);
        }

        if (invokeUser == null) {
            return handleNoAuth(response);
        }

         if (!accessKey.equals(invokeUser.getAccessKey())){
             return handleNoAuth(response);
       }

       //校验随机数
        if (Long.parseLong(nonce) > 10000L) {
            return handleNoAuth(response);
        }

        // 时间与当前时间不能超过五分钟
        Long currentTime = System.currentTimeMillis() / 1000;
        Long FIVE_MINUTES= 60 * 5L;
        if (currentTime - Long.parseLong(timestamp) >= FIVE_MINUTES){
             return handleNoAuth(response);
        }

        // 实际情况中是从数据库中查出 secretKey
        String secretKey = invokeUser.getSecretKey();
        String serverSign = SignUtils.getSign(body, secretKey);
        if (sign == null || !sign.equals(serverSign)) {
            return handleNoAuth(response);
        }


    // 5. 请求的模拟接口是否存在，以及请求方法是否匹配
        InterfaceInfo interfaceInfo = null;
        try {
            interfaceInfo = innerInterfaceInfoService.getInterfaceInfo(path, method);
        } catch (Exception e) {
            log.error("getInterfaceInfo error", e);
        }

        if (interfaceInfo == null) {
            return handleNoAuth(response);
        }
//        6. 请求转发，调用模拟接口
//        7. 响应日志
//        8. 调用成功，接口调用次数 + 1
//        9. 调用失败，返回一个规范的错误码
        //6-9都在下面的方法中执行。
        return handleResponse(exchange,chain,interfaceInfo.getId(), invokeUser.getId());
    }


    /**
     * 处理响应
     *
     * @param exchange
     * @param chain
     * @return
     */
    public Mono<Void> handleResponse(ServerWebExchange exchange, GatewayFilterChain chain, long interfaceInfoId, long userId) {
        try {
            ServerHttpResponse originalResponse = exchange.getResponse();
            // 缓存数据的工厂
            DataBufferFactory bufferFactory = originalResponse.bufferFactory();
            // 拿到响应码
            HttpStatus statusCode = originalResponse.getStatusCode();
            if (statusCode == HttpStatus.OK) {
                // 装饰，增强能力
                ServerHttpResponseDecorator decoratedResponse = new ServerHttpResponseDecorator(originalResponse) {
                    // 等调用完转发的接口后才会执行
                    @Override
                    public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
                        log.info("body instanceof Flux: {}", (body instanceof Flux));
                        if (body instanceof Flux) {
                            Flux<? extends DataBuffer> fluxBody = Flux.from(body);
                            // 往返回值里写数据
                            // 拼接字符串
                            return super.writeWith(
                                    fluxBody.map(dataBuffer -> {
                                        // 7. 调用成功，接口调用次数 + 1 invokeCount
                                        try {
                                            innerUserInterfaceInfoService.invokeCount(interfaceInfoId, userId);
                                        } catch (Exception e) {
                                            log.error("invokeCount error", e);
                                        }
                                        byte[] content = new byte[dataBuffer.readableByteCount()];
                                        dataBuffer.read(content);
                                        DataBufferUtils.release(dataBuffer);//释放掉内存
                                        // 构建日志
                                        StringBuilder sb2 = new StringBuilder(200);
                                        List<Object> rspArgs = new ArrayList<>();
                                        rspArgs.add(originalResponse.getStatusCode());
                                        String data = new String(content, StandardCharsets.UTF_8); //data
                                        sb2.append(data);
                                        // 打印日志
                                        log.info("响应结果：" + data);
                                        return bufferFactory.wrap(content);
                                    }));
                        } else {
                            // 8. 调用失败，返回一个规范的错误码
                            log.error("<--- {} 响应code异常", getStatusCode());
                        }
                        return super.writeWith(body);
                    }
                };
                // 设置 response 对象为装饰过的
                return chain.filter(exchange.mutate().response(decoratedResponse).build());
            }
            return chain.filter(exchange); // 降级处理返回数据
        } catch (Exception e) {
            log.error("网关处理响应异常" + e);
            return chain.filter(exchange);
        }
    }


    private Mono<Void> handleNoAuth(ServerHttpResponse response) {
        response.setStatusCode(HttpStatus.FORBIDDEN);
        return response.setComplete();
    }

    public Mono<Void> handleInvokeError(ServerHttpResponse response) {
        response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
        return response.setComplete();
    }

    @Override
    public int getOrder() {
        return -1;
    }
}