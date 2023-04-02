package com.yupi.project.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.quanhui.huiapicommon.model.entity.UserInterfaceInfo;

import java.util.List;

/**
* @author 邱权辉
* @description 针对表【user_interface_info(用户调用接口关系)】的数据库操作Mapper
* @createDate 2023-03-28 20:17:57
* @Entity com.yupi.project.model.entity.UserInterfaceInfo
*/
public interface UserInterfaceInfoMapper extends BaseMapper<UserInterfaceInfo> {

    List<UserInterfaceInfo> listTopInvokeInterfaceInfo(int limit);
}




