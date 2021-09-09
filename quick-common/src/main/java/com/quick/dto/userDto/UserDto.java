package com.quick.dto.userDto;

import lombok.Data;

import java.util.Date;

/**
 * @author ：彭来祥
 * @date ：Created in 2020/9/21 9:21
 * @description：用户
 * @version: $
 */
@Data
public class UserDto extends BaseDto {

    /**
     * id
     */
    private String userId;
    /**
     * 密码
     */
    private String userPassword;
    /**
     * 支付宝用户id
     */
    private String alipayUserId;
    /**
     * 微信用户id
     */
    private String wxUserId;
    /**
     * 手机号
     */
    private String  phoneNo;
    /**
     * 没有被混淆过的手机号
     */
    private String  userPhone;
    /**
     * 用户名
     */
    private String  userName;
    /**
     * 照片地址
     */
    private String  photoUrl;
    /**
     * 照片缩略图地址
     */
    private String  photoThumbUrl;
    /**
     * 实名认证标志
     */
    private Integer  certifyFlg;
    /**
     * 最近登录时间
     */
    private Date lastTime;
    /**
     * 注册时间
     */
    private Date registTime;
    /**
     * 登录方式
     */
    private String type;
    /**
     * token
     */
    private String token;
    /**
     * code
     */
    private String code;
    /**
     * pushID
     */
    private String pushId;

    public UserDto(String phoneNo, String type) {
        this.phoneNo = phoneNo;
        this.type = type;
    }

    public UserDto() {
    }
}
