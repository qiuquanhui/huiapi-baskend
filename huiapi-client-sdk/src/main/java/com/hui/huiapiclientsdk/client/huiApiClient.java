package com.hui.huiapiclientsdk.client;/**
 * 作者:灰爪哇
 * 时间:2023-03-23
 */

import cn.hutool.core.util.RandomUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.hui.huiapiclientsdk.entity.User;
import com.hui.huiapiclientsdk.utils.SignUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: shuaihui
 **/
public class huiApiClient {

    public static final String GATEWAY_HOST = "http://localhost:8090";

    //许可证
    private String accessKey;

    //密钥
    private String secretKey;

    //初始化构造器
    public huiApiClient(String accessKey, String secretKey) {
        this.accessKey = accessKey;
        this.secretKey = secretKey;
    }


    //作为请求头
    private Map<String,String> getHeaderMap(String body){
        HashMap<String, String> headers = new HashMap<>();
        headers.put("accessKey",accessKey);       //ac 许可证
        headers.put("nonce", RandomUtil.randomNumbers(4));  //随机数
        headers.put("body",body);                     //userjson
        headers.put("timestamp",String.valueOf(System.currentTimeMillis()/1000));  //时间戳
        headers.put("sign", SignUtils.getSign(body,secretKey));       //签名认证
        return headers;
    }




    public String getNameByGet(String name) {
        //可以单独传入http参数，这样参数会自动做URL编码，拼接在URL中
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("name", name);

        String result = HttpUtil.get(GATEWAY_HOST + "/api/name/", paramMap);
        System.out.println(result);
        return result;
    }

    public String getNameByPost(String name) {
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("name", name);

        String result = HttpUtil.post(GATEWAY_HOST +"/api/name/", paramMap);
        System.out.println(result);
        return result;

    }

    public String getUsernameByPost(User user) {

        String json = JSONUtil.toJsonStr(user);

        HttpResponse httpResponse = HttpRequest.post(GATEWAY_HOST + "/api/name/user")
                .addHeaders(getHeaderMap(json))
                .body(json)
                .execute();

        System.out.println(httpResponse.getStatus());
        String result = httpResponse.body();
        System.out.println(result);
        return result;
    }

    //调加一个调用方法
    public String getMotivationalWords() {


        HttpResponse httpResponse = HttpRequest.post(GATEWAY_HOST + "/api/words/motivation")
                .addHeaders(getHeaderMap("1"))
                .body("1")
                .execute();

        System.out.println(httpResponse.getStatus());
        String result = httpResponse.body();
        System.out.println(result);
        return result;
    }
}
