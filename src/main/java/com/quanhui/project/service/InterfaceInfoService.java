package com.quanhui.project.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.quanhui.huiapicommon.model.entity.InterfaceInfo;

/**
* @author 邱权辉
* @description 针对表【interface_info(接口信息)】的数据库操作Service
* @createDate 2023-03-21 20:01:30
*/
public interface InterfaceInfoService extends IService<InterfaceInfo> {

    //校验
    void validInterfaceInfo(InterfaceInfo interfaceInfo, boolean b);
}
