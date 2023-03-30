package com.yupi.project.service;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * 作者:灰爪哇
 * 时间:2023-03-28
 */
@SpringBootTest
public class UserInterfaceInfoServiceTest {

    @Resource UserInterfaceInfoService userInterfaceInfoService;

    @Test
    public void invokeCount() {
        Boolean result = userInterfaceInfoService.invokeCount(1L, 1L);
        Assertions.assertTrue(result);
    }
}