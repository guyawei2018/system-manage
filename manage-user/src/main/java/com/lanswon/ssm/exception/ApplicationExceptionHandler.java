package com.lanswon.ssm.exception;

import com.lanswon.base.error.ErrorCode;
import com.lanswon.base.log.LogFactory;
import com.lanswon.base.log.LogLevel;
import com.lanswon.base.support.SimpleResponse;
import com.lanswon.ssm.domain.contants.UumContant;
import com.lanswon.ssm.util.HttpServletRequestUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;

/**
 * @Description: 统一异常处理
 * @Author GU-YW
 * @Date 2019/12/3 16:54
 */
@ControllerAdvice
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ApplicationExceptionHandler {

    private final LogFactory logFactory;

    @ExceptionHandler
    @ResponseBody
    public SimpleResponse handleException(Exception ex, HttpServletRequest request) throws UnknownHostException {
        log.error("exception=[{}]",ex.getClass());
        log.error("===系统出现异常[{}]===",ex.getMessage());
        String token = request.getHeader("token");
        ex.printStackTrace();
        SimpleResponse simpleResponse = SimpleResponse
                .builder()
                .code(Integer.valueOf(ErrorCode.SSM_SYSTEM_EXCEPTION.code))
                .build();
        if(ex instanceof ApplicationException){
            simpleResponse.setCode(((ApplicationException) ex).getErrorCode());
            simpleResponse.setMsg(ex.getMessage());
        }else if(ex instanceof MethodArgumentNotValidException){
            StringBuilder sb=new StringBuilder();
            for (ObjectError error : ((MethodArgumentNotValidException) ex).getBindingResult().getAllErrors()) {
                sb.append(error.getDefaultMessage());
            }
            simpleResponse.setMsg(sb.toString());
        }
        else if(ex instanceof Exception){
            simpleResponse.setMsg("系统出现异常=["+ex.getMessage()+"]");
        }
        UumContant.LOGQUEUE.add(logFactory.
                getInstance(-2, HttpServletRequestUtil.getIp(request), LogLevel.ERROR.code,
                        simpleResponse.getMsg(),"系统用户管理系统"));
        return simpleResponse;
    }
}
