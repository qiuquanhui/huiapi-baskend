package com.quanhui.huiapicommon.service;

import com.quanhui.huiapicommon.model.entity.UserInterfaceInfo;

/**
 *
 */
public interface InnerUserInterfaceInfoService {

    /**
     * 调用接口统计
     * @param interfaceInfoId
     * @param userId
     * @return
     */
    boolean invokeCount(long interfaceInfoId, long userId);

    /*

     获取当前接口与用户
    */
    UserInterfaceInfo getUserInterfaceInfo(long interfaceInfoId, long userId);
}
