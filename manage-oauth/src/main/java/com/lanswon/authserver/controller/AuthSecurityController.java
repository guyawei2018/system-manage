/**
 * 
 */
package com.lanswon.authserver.controller;

import com.lanswon.authcore.contants.SecurityConstants;
import com.lanswon.authcore.support.SimpleResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 跳转身份认证
 * @Author GU-YW
 * @Date 2019/10/29 8:18
 */
@RestController
@Slf4j
public class AuthSecurityController {

	/**
	 * 当需要身份认证时，跳转到这里
	 *
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(SecurityConstants.DEFAULT_UNAUTHENTICATION_URL)
	@ResponseStatus(code = HttpStatus.UNAUTHORIZED)
	public SimpleResponse requireAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
			String targetUrl = request.getRequestURI();
			log.info("引发跳转的请求是:" + targetUrl);
		return SimpleResponse.ok("访问的服务需要身份认证，请先登录");
	}


}
