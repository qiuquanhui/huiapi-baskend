/*
 * Copyright 2013-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.hui.huiapiinterface.controller;

import com.hui.huiapiclientsdk.entity.User;
import com.hui.huiapiclientsdk.utils.SignUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author <a href="mailto:chenxilzx1@gmail.com">theonefx</a>
 */
@RestController
@RequestMapping("/name")
public class NameController {

    @GetMapping("/get")
    public String getNameByGet(String name){
        return "Get 你的名字是" + name;
    }

    @PostMapping("/post")
    public String getNameByPost(@RequestParam String name){
        return "Post 你的名字是" + name;
    }

    @PostMapping("/user")
    public String getUserNameByPost(@RequestBody User user, HttpServletRequest request){
        //从请求头中取出数据
        String accessKey = request.getHeader("accessKey");
        String nonce = request.getHeader("nonce");
        String timestamp = request.getHeader("timestamp");
        String sign = request.getHeader("sign");
        String body = request.getHeader("body");
        //todo 从数据库中查询是否已分配给用户
        if (!accessKey.equals("hui")){
            throw new RuntimeException("无权限");
        }

        //todo 校验随机数
        if (Long.parseLong(nonce) > 10000){
            throw new RuntimeException("无权限");
        }

        //todo 时间与当前时间不能超过五分钟
       // if (timestamp){}

        //todo 实际情况是从数据库中查出
        String serverSign = SignUtils.getSign(body, "abcdefg");


        if (!accessKey.equals("hui") ){
            throw new RuntimeException("无权限");
        }

        //签名认证
        if (!sign.equals(serverSign)){
            throw  new RuntimeException("无权限");
        }


        return "Post 你的名字是" + user.getUsername();
    }

}
