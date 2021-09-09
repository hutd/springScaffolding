package com.quick.api;

import com.quick.dto.userDto.ClinicalUserInfoDto;
import com.quick.vo.IResultEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/userModel")
public interface UserApi {
    @GetMapping("/testDB")
    IResultEntity<String> testDB(@Validated @ModelAttribute ClinicalUserInfoDto userInfo);
}
