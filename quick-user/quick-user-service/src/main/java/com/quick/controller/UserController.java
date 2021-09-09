package com.quick.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.quick.client.InnerClient;
import com.quick.config.CurrentLimitingAndBlockingConfig;
import com.quick.config.RequestBodyFactory;
import com.quick.dto.testReqDto;
import com.quick.dto.userDto.ClinicalUserInfoDto;
import com.quick.manager.HttpServiceManager;
import com.quick.service.UserService;
import com.quick.utils.RedisUtils;
import com.quick.vo.IResultEntity;
import com.quick.vo.ResultEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import okhttp3.Request;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@Api(tags = "用户模块")
@RequestMapping("/userModel")
public class UserController {

    /**
     * feign远程调用
     */
    @Resource
    private InnerClient innerClient;

    @Resource
    private RedisUtils redisUtils;

    @Resource
    private UserService userService;

    @GetMapping("/testDB")
    @ApiOperation(value = "测试数据库调用的方法")
    public IResultEntity<String> testDB(@Validated @RequestBody ClinicalUserInfoDto userInfo) {
        // todo  测试数据库连接
        userService.insertUserInfo(userInfo);
        List<String> userInfo1 = userService.getUserInfo(userInfo);
        return ResultEntity.ok("OK");
    }

    @GetMapping("/testFeign")
    @ApiOperation(value = "测试feign远程调用的方法")
    public IResultEntity<String> testFeign() {
        // todo  feign远程调用inner方法测试
        return ResultEntity.ok(innerClient.test());
    }

    @GetMapping("/testOKHttp")
    @ApiOperation(value = "测试OKHttp远程调用的方法")
    public IResultEntity<Object> testOKHttp() {
        Request request = new RequestBodyFactory.GetBodyBuilder()
//                .add("参数1", "1111")
//                .add("参数2", "2222")
                .url("www.baidu.com")
                .build();
//        testReqDto execute = (testReqDto) HttpServiceManager.getInstance().execute(request, testReqDto.class, true);
        return ResultEntity.ok(request);
    }

    @GetMapping("/testRedis")
    @ApiOperation(value = "测试Redis的方法")
    @SentinelResource(value = "testRedis", blockHandlerClass = CurrentLimitingAndBlockingConfig.class,
            blockHandler = "blockHandlerFunc", fallback = "fallbackFunc")
    public IResultEntity<String> testRedis() {
        redisUtils.set("test","test");
        return ResultEntity.ok(redisUtils.get("test").toString());
    }

    @GetMapping("/testSentinel")
    @ApiOperation(value = "测试sentinel的方法")
    @SentinelResource(value = "testSentinel", blockHandlerClass = CurrentLimitingAndBlockingConfig.class,
            blockHandler = "blockHandlerFunc", fallback = "fallbackFunc")
    public IResultEntity<String> testSentinel() {
        return ResultEntity.ok("OK");
    }

}
