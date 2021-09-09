package com.quick.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags="")
@RestController
@RequestMapping("/demo")
public class demoController {

    @GetMapping("/test")
    @ApiOperation(value="测试feign方法")
    public String test() {
        return "远程feign调用成功";
    }

}
