package com.lanswon.authserver.controller;

import com.lanswon.authcore.validatecode.ValidateCode;
import com.lanswon.authserver.service.UserService;
import com.lanswon.base.contant.BaseContant;
import com.lanswon.base.support.SimpleResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * @Description:
 * @Author: GU-YW
 * @Date: 2020/8/26 10:54
 */
@RestController
@Slf4j
public class UserController {

    @Autowired
    UserService userService;

    @Resource
    RedisTemplate<String,Object> redisTemplate;

    @GetMapping("/oauth/user/info")
    public SimpleResponse getUser(String phone){
        return userService.getUser(phone);
    }

    @GetMapping("/oauth/check/sms")
    public SimpleResponse getUser( String phone,String code){
        if(Objects.isNull(phone) || Objects.isNull(code)){
            throw new NullPointerException("手机号码不能为空");
        }else{
            Object value = redisTemplate.opsForValue().get(BaseContant.SMSCODE+phone);
            if (value == null) {
                return null;
            }
            log.debug("redis获取的ValidateCode=[{}]",(ValidateCode) value);
            boolean equals = ((ValidateCode) value).getCode().equals(code);
            if(equals){
                return SimpleResponse.builder().code(HttpStatus.OK.value()).msg("校验成功").build();
            }else {
                return SimpleResponse.builder().code(HttpStatus.INTERNAL_SERVER_ERROR.value()).msg("验证码错误,请重新输入").build();
            }
        }

    }
}
