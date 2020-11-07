/**
 * 
 */
package com.lanswon.authapp.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lanswon.authcore.contants.LoginResponseType;
import com.lanswon.authcore.contants.MessageQueue;
import com.lanswon.authcore.properties.SecurityProperties;
import com.lanswon.authcore.support.SimpleResponse;
import com.lanswon.authserver.util.HttpServletRequestUtil;
import com.lanswon.base.log.LogDto;
import com.lanswon.base.log.LogFactory;
import com.lanswon.base.log.LogLevel;
import com.lanswon.base.log.LogType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自定义认证失败处理
 * @author GU-YW
 */
@Component("customAuthenticationFailureHandler")
@Slf4j
public class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired(required = false)
	private SecurityProperties securityProperties;

	@Autowired
	private LogFactory logFactory;

	
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		log.info("登录失败");

		LogDto instance = logFactory.getInstance(null,null,null,null, null, LogType.LOGIN.code,
				HttpServletRequestUtil.getIp(request), LogLevel.ERROR.code, null, null, "登陆认证失败", "安全认证服务");

		MessageQueue.LOGQUEUE.add(instance);

		if (LoginResponseType.JSON.equals(securityProperties.getBrowser().getLoginType())) {
			response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
			response.setContentType("application/json;charset=UTF-8");
			response.getWriter().write(objectMapper.writeValueAsString(SimpleResponse.ok(exception.getMessage())));
		}else{
			super.onAuthenticationFailure(request, response, exception);
		}
		
		
	}

}
