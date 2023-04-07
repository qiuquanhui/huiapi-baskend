package com.hui.huiapiinterface.controller;/**
 * 作者:灰爪哇
 * 时间:2023-04-03
 */

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

/**
 *
 *
 * @author: shuaihui
 **/
@RestController
@RequestMapping("/words")
public class WordController {

    String [] motivationalWords = {"君子一言，驷马难追","花自花香，蝴蝶自来","你尽管努力，剩下的交给天意"};

    @GetMapping("/motivation")
    public String getWordsByGet(String name){
        //1.获取一个 0 - 2 的随机数
        Random random = new Random();
        int randomNumber = random.nextInt(3);
        return motivationalWords[randomNumber];
    }
}
