package com.quanhui.project.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.quanhui.huiapicommon.model.entity.UserInterfaceInfo;
import com.quanhui.project.common.ErrorCode;
import com.quanhui.project.exception.BusinessException;
import com.quanhui.project.mapper.UserInterfaceInfoMapper;
import com.quanhui.project.service.UserInterfaceInfoService;
import org.springframework.stereotype.Service;

/**
 * @author 邱权辉
 * @description 针对表【user_interface_info(用户调用接口关系)】的数据库操作Service实现
 * @createDate 2023-03-28 20:17:57
 */
@Service
public class UserInterfaceInfoServiceImpl extends ServiceImpl<UserInterfaceInfoMapper, UserInterfaceInfo>
        implements UserInterfaceInfoService {

    @Override
    public void validUserInterfaceInfo(UserInterfaceInfo userInterfaceInfo, boolean add) {


        if (userInterfaceInfo == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        // 创建时，所有参数必须非空
        if (add) {
            if (userInterfaceInfo.getInterfaceInfoId() <= 0 || userInterfaceInfo.getId() <= 0) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR, "接口或者用户名不存在");
            }
        }
        if (userInterfaceInfo.getLeftNum() < 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "剩余次数小于 0 ");
        }
    }

    @Override
    public Boolean invokeCount(Long interfaceInfoId, long userId) {

        //todo 多校验
        //校验
        if (interfaceInfoId <= 0 || userId <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        UpdateWrapper<UserInterfaceInfo> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("interfaceInfoId", interfaceInfoId);
        updateWrapper.eq("userId", userId);
        updateWrapper.setSql("leftNum = leftNum - 1,totalNum = totalNum + 1");
        return this.update(updateWrapper);
    }

    @Override
    public UserInterfaceInfo getUserInterfaceInfo(long interfaceInfoId, long userId) {
        //查询数据库看看有没有这个数据
        QueryWrapper<UserInterfaceInfo> userInterfaceInfoQueryWrapper = new QueryWrapper<>();
        userInterfaceInfoQueryWrapper.eq("interfaceInfoId",interfaceInfoId);
        userInterfaceInfoQueryWrapper.eq("userId",userId);
        UserInterfaceInfo userInterfaceInfo = this.getOne(userInterfaceInfoQueryWrapper);
        //如果没有就新增
        if (userInterfaceInfo == null){
            UserInterfaceInfo newUserInterfaceInfo = new UserInterfaceInfo();
            newUserInterfaceInfo.setUserId(userId);
            newUserInterfaceInfo.setInterfaceInfoId(interfaceInfoId);
            this.save(newUserInterfaceInfo);
            newUserInterfaceInfo = this.getOne(userInterfaceInfoQueryWrapper);
            return newUserInterfaceInfo;
        }else{
            return userInterfaceInfo;
        }
    }

}







