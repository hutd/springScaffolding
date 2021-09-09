package com.quick.mapper;

import com.quick.dto.userDto.ClinicalUserInfoDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper {


    void insertUserInfo(ClinicalUserInfoDto userInfo);

    @Select("select USER_NAME from T_USER")
    List<String> getUserInfo(ClinicalUserInfoDto userInfo);
}
