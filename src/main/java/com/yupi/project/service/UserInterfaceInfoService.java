package com.yupi.project.service;

import com.yupi.project.model.entity.UserInterfaceInfo;
import com.baomidou.mybatisplus.extension.service.IService;

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

}
