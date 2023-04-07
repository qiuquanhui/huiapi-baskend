package com.yupi.project.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.quanhui.huiapicommon.model.entity.UserInterfaceInfo;


/**
* @author 邱权辉
* @description 针对表【user_interface_info(用户调用接口关系)】的数据库操作Service
* @createDate 2023-03-28 20:17:57
*/
public interface UserInterfaceInfoService extends IService<UserInterfaceInfo> {

    //非法校验
    void validUserInterfaceInfo(UserInterfaceInfo userInterfaceInfo, boolean add);


    /*
     * @调用接口统计
        * @param interfaceInfoId
        * @param userId
     * @return int
    */
    Boolean invokeCount(Long interfaceInfoId,long userId);

    //获取当前用户接口信息，没有就新增
    UserInterfaceInfo getUserInterfaceInfo(long interfaceInfoId, long userId);
}
