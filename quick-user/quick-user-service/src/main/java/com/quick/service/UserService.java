package com.quick.service;

import com.quick.dto.userDto.ClinicalUserInfoDto;
import com.quick.enums.ExceptionEnum;
import com.quick.exception.CamelliaException;
import com.quick.mapper.UserMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserService {

    @Resource
    private UserMapper userMapper;

    public void insertUserInfo(ClinicalUserInfoDto userInfo) {
        userMapper.insertUserInfo(userInfo);
    }

    public List<String> getUserInfo(ClinicalUserInfoDto userInfo) {
        try {
            return userMapper.getUserInfo(userInfo);
        } catch (Exception e) {
            // 失败则抛出异常，在BasicExceptionHandler中进行捕获
            throw new CamelliaException(ExceptionEnum.INVALID_PARAM);
        }

    }
}
