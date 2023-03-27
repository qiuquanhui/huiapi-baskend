package com.hui.huiapiinterface;

import com.hui.huiapiclientsdk.client.huiApiClient;
import com.hui.huiapiclientsdk.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class HuiapiInterfaceApplicationTests {

    @Resource
    private huiApiClient huiApiClient;

    @Test
    void contextLoads() {

        String result1 = huiApiClient.getNameByGet("hui");
        String result2 = huiApiClient.getNameByPost("hui");
        String result3 = huiApiClient.getUsernameByPost(new User("hui"));


        System.out.println(result1);
        System.out.println(result2);
        System.out.println(result3);
    }

}
