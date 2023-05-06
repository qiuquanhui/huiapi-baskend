package com.quanhui.project.service.impl.inner;

import com.quanhui.huiapicommon.model.entity.UserInterfaceInfo;
import com.quanhui.huiapicommon.service.InnerUserInterfaceInfoService;
import com.quanhui.project.service.UserInterfaceInfoService;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;

@DubboService
public class InnerUserInterfaceInfoServiceImpl implements InnerUserInterfaceInfoService {

    @Resource
    private UserInterfaceInfoService userInterfaceInfoService;


    // 记录调用次数
    @Override
    public boolean invokeCount(long interfaceInfoId, long userId) {
        return userInterfaceInfoService.invokeCount(interfaceInfoId, userId);
    }

    //获取当前用户的信息
    @Override
    public UserInterfaceInfo getUserInterfaceInfo(long interfaceInfoId, long userId) {

        return userInterfaceInfoService.getUserInterfaceInfo(interfaceInfoId, userId);
    }
}
