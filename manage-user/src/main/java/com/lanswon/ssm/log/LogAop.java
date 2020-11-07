package com.lanswon.ssm.log;

import com.lanswon.base.contant.BaseContant;
import com.lanswon.base.log.LogFactory;
import com.lanswon.base.log.LogLevel;
import com.lanswon.base.log.LogType;
import com.lanswon.ssm.domain.contants.UumContant;
import com.lanswon.ssm.util.HttpServletRequestUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;

/**
 * @Description:
 * @Author: GU-YW
 * @Date: 2020/7/15 14:35
 */
@Slf4j
@Aspect
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class LogAop {

    private final LogFactory logFactory;

    @Pointcut("execution(public * com.lanswon.ssm.controller.*.*(..))")
    public void anyMethod(){}

    @After("anyMethod()")
    public void collectionLog(JoinPoint joinPoint) throws UnknownHostException {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra =(ServletRequestAttributes)requestAttributes;
        HttpServletRequest request = sra.getRequest();
        String token = request.getHeader("token");
        log.debug("请求中携带TOKEN =[{}]",token);


        MethodSignature signature = (MethodSignature)joinPoint.getSignature();

        Api apiAnnotation = joinPoint.getTarget().getClass().getAnnotation(Api.class);
        String apiValue = apiAnnotation.tags()[0];

        ApiOperation apiOperationAnnotation = signature.getMethod().getAnnotation(ApiOperation.class);
        String methodValue = apiOperationAnnotation.value();

        //TODO 登录前排除特殊的请求
        if(!StringUtils.isEmpty(token)){

            if(!StringUtils.isEmpty(apiValue) && !StringUtils.isEmpty(methodValue)){
                int logtype = 1;

                if(methodValue.contains("新增")){
                    logtype = LogType.SELECT.code;
                }else if(methodValue.contains("修改")){
                    logtype = LogType.PUT.code;
                }else if(methodValue.contains("删除")){
                    logtype = LogType.DELETE.code;
                }else if(methodValue.contains("查询")){
                    logtype = LogType.SELECT.code;
                }
                try {
                    if(token.startsWith(BaseContant.APP_TOKEN_PREFIX)){


                    }else{
                        UumContant.LOGQUEUE.add(logFactory.
                                getInstance(token, logtype, HttpServletRequestUtil.getIp(request), LogLevel.OK.code,
                                        methodValue,apiValue));
                    }

                }catch (Exception ex){
                    UumContant.LOGQUEUE.add(logFactory.
                            getInstance(-2, HttpServletRequestUtil.getIp(request), LogLevel.ERROR.code,
                                    ex.getMessage(),"系统用户管理系统"));
                }

            }
        }else{
            UumContant.LOGQUEUE.add(logFactory.
                    getInstance(LogType.SELECT.code, HttpServletRequestUtil.getIp(request), LogLevel.OK.code,
                            methodValue,apiValue));
        }

    }
}
