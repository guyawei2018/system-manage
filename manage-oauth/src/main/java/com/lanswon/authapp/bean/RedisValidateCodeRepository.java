package com.lanswon.authapp.bean;

import com.lanswon.authcore.contants.ValidateCodeType;
import com.lanswon.authcore.validatecode.ValidateCode;
import com.lanswon.authcore.validatecode.ValidateCodeException;
import com.lanswon.authcore.validatecode.ValidateCodeRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @Author GU-YW
 * @Date 2019/11/11 16:11
 *  Description：使用redis+deviceId的方式进行验证码的存、取、删
 */
@Component
@Slf4j
public class RedisValidateCodeRepository implements ValidateCodeRepository {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public void save(ServletWebRequest request, ValidateCode code, ValidateCodeType type) {
        redisTemplate.opsForValue().set(buildKey(request, type), code, 180, TimeUnit.SECONDS);
    }

    @Override
    public ValidateCode get(ServletWebRequest request, ValidateCodeType type) {
        Object value = redisTemplate.opsForValue().get(buildKey(request, type));
        if (value == null) {
            return null;
        }
        log.debug("redis获取的ValidateCode=[{}]",(ValidateCode) value);
        return (ValidateCode) value;
    }

    @Override
    public void remove(ServletWebRequest request, ValidateCodeType type) {
        redisTemplate.delete(buildKey(request, type));
    }

    /**
     * 构建验证码在redis中的key ---- 该key类似与cookie
     * @param request
     * @param type
     * @return
     */
    private String buildKey(ServletWebRequest request, ValidateCodeType type) {
        String deviceId = request.getParameter("deviceId");
        if (StringUtils.isBlank(deviceId)) {
            throw new ValidateCodeException("请在请求中携带deviceId参数");
        }
        String key = "code:" + type.toString().toLowerCase() + ":" + deviceId;
        log.debug("生成的key=[{}]",key);
        return key;
    }
}
