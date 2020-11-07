package com.lanswon.ssm.service;

import com.lanswon.base.contant.BaseContant;
import com.lanswon.base.support.JWTKey;
import com.lanswon.ssm.vo.OauthUser;
import com.lanswon.ssm.vo.UserInfo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.Data;
import org.springframework.data.redis.core.RedisTemplate;

import java.io.UnsupportedEncodingException;
import java.util.Objects;

/**
 * @Description:
 * @Author: GU-YW
 * @Date: 2020/6/29 17:53
 */
@Data
public class UserService {



    RedisTemplate<String, Object> redisTemplate;

    /**
     * 获取当前用户信息
     * @param token
     * @return
     */
    public UserInfo getCurrentUser(String token) throws UnsupportedEncodingException {
        Claims claims = Jwts.parser().setSigningKey(JWTKey.key.getBytes("UTF-8"))
                .parseClaimsJws(token).getBody();
        String key = BaseContant.USERCODE +claims.get("user_name");
        Object object = redisTemplate.opsForValue().get(key);
        if(Objects.isNull(object)){
            return null;
        }
        return (UserInfo)object;
    }

    /**
     * 获取当前用户信息
     * @param sfzhm
     * @return
     */
    public UserInfo getCurrentUserById(String sfzhm) throws UnsupportedEncodingException {
        String key = BaseContant.USERCODE + sfzhm;
        Object object = redisTemplate.opsForValue().get(key);
        if(Objects.isNull(object)){
            return null;
        }
        return (UserInfo)object;
    }

    /**
     * 获取当前用户权限信息
     * @param sfzhm
     * @return
     */
    public OauthUser getOauth(String sfzhm){
        String key = BaseContant.OAUTHCODE+sfzhm;
        Object object = redisTemplate.opsForValue().get(key);
        if(Objects.isNull(object)){
            return null;
        }
        return (OauthUser)object;
    }


    public void setCurrentUser(UserInfo userInfo) {
        String key = BaseContant.USERCODE + userInfo.getSfzhm();
        redisTemplate.opsForValue().set(key,userInfo);
    }

    public void setOauth(OauthUser oauthUser) {
        String key = BaseContant.OAUTHCODE + oauthUser.getSfzhm();
        redisTemplate.opsForValue().set(key,oauthUser);
    }


}
