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
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Random;

/**
 * @author <a href="mailto:chenxilzx1@gmail.com">theonefx</a>
 */
@RestController
@RequestMapping("/name")
public class NameController {

    String[] motivationalWords = {"君子一言，驷马难追", "花自花香，蝴蝶自来", "你尽管努力，剩下的交给天意"};

    @GetMapping("/get")
    public String getNameByGet(String name) {
        return "Get 你的名字是" + name;
    }

    @PostMapping("/post")
    public String getNameByPost(@RequestParam String name) {
        return "Post 你的名字是" + name;
    }

    @PostMapping("/user")
    public String getUserNameByPost(@RequestBody User user, HttpServletRequest request) {

        //1.获取一个 0 - 2 的随机数
        Random random = new Random();
        int randomNumber = random.nextInt(3);
        return motivationalWords[randomNumber] + user.getUsername();
    }
}
