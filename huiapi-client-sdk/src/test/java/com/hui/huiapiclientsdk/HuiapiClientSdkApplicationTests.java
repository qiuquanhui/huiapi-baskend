package com.hui.huiapiclientsdk;

import com.hui.huiapiclientsdk.client.huiApiClient;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = HuiapiClientSdkApplicationTests.class)
class HuiapiClientSdkApplicationTests {


    @Test
    void contextLoads() {
        huiApiClient huiApiClient = new huiApiClient("w","w");
        String motivationalWords = huiApiClient.getMotivationalWords();
        System.out.println(motivationalWords);
    }

}
