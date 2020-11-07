package com.lanswon.base.log;

import com.lanswon.base.support.JWTKey;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.Data;
import org.springframework.data.redis.core.RedisTemplate;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

/**
 * @Description:
 * @Author: GU-YW
 * @Date: 2020/7/13 14:33
 */
@Data
public class LogFactory {

    RedisTemplate<String, Object> redisTemplate;

    /**
     * 获取登陆日志对象
     * @param userName
     * @param cardNo
     * @param policeNo
     * @param orgCode
     * @param orgName
     * @param logType
     * @param clientIp
     * @param logLevel
     * @param loginWay
     * @param userFace
     * @param logDetail
     * @param moudleName
     * @return
     */
    public LogDto getInstance(String userName,String cardNo,String policeNo,String orgCode,String orgName,Integer logType,String clientIp,Integer logLevel,Integer loginWay,
                              String userFace,String logDetail,String moudleName){
        return LogDto.builder()
                .userName(userName)
                .cardNo(cardNo)
                .policeNo(policeNo)
                .orgCode(orgCode)
                .orgName(orgName)
                .logType(logType)
                .terminalNo(clientIp)
                .operateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()))
                .logLevel(logLevel)
                .loginWay(loginWay)
                .userFace(userFace)
                .logDetail(logDetail)
                .moduleName(moudleName)
               .build();

    }

    /**
     * 获取业务日志对象
     * @param token
     * @param logType
     * @param clientIp
     * @param logLevel
     * @param logDetail
     * @param moudleName
     * @return
     */
    public LogDto getInstance(String token,Integer logType,String clientIp,Integer logLevel,String logDetail,String moudleName) throws UnsupportedEncodingException {
        Claims claims = Jwts.parser().setSigningKey(JWTKey.key.getBytes("UTF-8"))
                .parseClaimsJws(token).getBody();
        String key = "user:"+claims.get("user_name");
        Object o = redisTemplate.opsForValue().get(key);
        if(Objects.isNull(o)){
            return null;
        }
//        UserInfo userInfo = (UserInfo) o;
//        LogDto build = LogDto.builder()
//                .userName(userInfo.getYhmc())
//                .cardNo(userInfo.getSfzhm())
//                .policeNo(userInfo.getJh())
//                .orgCode(userInfo.getZzbm())
//                .orgName(userInfo.getZzmc())
//                .logType(logType)
//                .terminalNo(clientIp)
//                .operateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()))
//                .logLevel(logLevel)
//                .loginWay(0)
//                .userFace("")
//                .logDetail(logDetail)
//                .moduleName(moudleName)
//                .build();
        return null;

    }


    /**
     * 获取异常日志对象
     * @param logType
     * @param clientIp
     * @param logLevel
     * @param logDetail
     * @param moudleName
     * @return
     */
    public LogDto getInstance(Integer logType,String clientIp,Integer logLevel,String logDetail,String moudleName){

        LogDto build = LogDto.builder()
                .logType(logType)
                .terminalNo(clientIp)
                .operateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()))
                .logLevel(logLevel)
                .loginWay(0)
                .userFace("")
                .logDetail(logDetail)
                .moduleName(moudleName)
                .build();
        return build;

    }
}
