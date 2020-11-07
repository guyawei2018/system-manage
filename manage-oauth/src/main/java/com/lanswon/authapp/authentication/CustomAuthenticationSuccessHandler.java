/**
 * 
 */
package com.lanswon.authapp.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lanswon.authcore.contants.MessageQueue;
import com.lanswon.authcore.properties.SecurityProperties;
import com.lanswon.authcore.token.AnonymousToken;
import com.lanswon.authserver.domain.entity.TYhxx;
import com.lanswon.authserver.util.HttpServletRequestUtil;
import com.lanswon.base.contant.BaseContant;
import com.lanswon.base.log.LogDto;
import com.lanswon.base.log.LogFactory;
import com.lanswon.base.log.LogLevel;
import com.lanswon.base.log.LogType;
import com.lanswon.ssm.service.UserService;
import com.lanswon.ssm.vo.OauthUser;
import com.lanswon.ssm.vo.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.UnapprovedClientAuthenticationException;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.social.security.SocialUser;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Base64;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * 自定义认证成功处理
 * 继承SavedRequestAwareAuthenticationSuccessHandler(spring默认使用)可以更好的扩展需求,比实现AuthenticationSuccessHandler接口好
 * @author GU-YW
 */
@Slf4j
@Component("customAuthenticationSuccessHandler")
public class CustomAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

	@Autowired
	private ObjectMapper objectMapper;

	@Resource
	private AuthorizationServerTokenServices authorizationServerTokenServices;

	@Autowired
	private ClientDetailsService clientDetailsService;

	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private RedisTemplate<String,Object> redisTemplate;

	@Autowired
	private SecurityProperties securityProperties;

	@Autowired
	private UserService userService;

	@Autowired
	private LogFactory logFactory;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException {
		log.info("登录成功");
		//请求头的Authorization里存放了ClientId和ClientSecret
		String header = request.getHeader("Authorization");

		if (header == null || !header.startsWith("Basic ")) {
			throw new UnapprovedClientAuthenticationException("请求头中无client信息");
		}

		String[] tokens = extractAndDecodeHeader(header, request);
		assert tokens.length == 2;

		String clientId = tokens[0];
		String clientSecret = tokens[1];

		// 根据clientId获取ClientDetails对象 --- ClientDetails为第三方应用的信息
		// 现在配置在了yml文件里，真实项目中应该放在数据库里
		ClientDetails clientDetails = clientDetailsService.loadClientByClientId(clientId);

		log.debug("系统内部的clientSecret=[{}]",passwordEncoder.matches(clientSecret,clientDetails.getClientSecret()));
		// 对获取到的clientDetails进行校验
		if (clientDetails == null) {
			throw new UnapprovedClientAuthenticationException("clientId对应的配置信息不存在:" + clientId);
		} else if (!passwordEncoder.matches(clientSecret,clientDetails.getClientSecret())) {
			throw new UnapprovedClientAuthenticationException("clientSecret不匹配:" + clientId);
		}

		//第一个参数为请求参数的一个Map集合，
		// 在Spring Security OAuth的源码里要用这个Map里的用户名+密码或授权码来生成Authentication对象,
		// 但我们已经获取到了Authentication对象，所以这里可以直接传一个空的Map
		//第三个参数为scope即请求的权限 ---》这里的策略是获得的ClientDetails对象里配了什么权限就给什么权限
		//第四个参数为指定什么模式 比如密码模式为password，授权码模式为authorization_code，
		// 这里我们写一个自定义模式custom
		TokenRequest tokenRequest = new TokenRequest(MapUtils.EMPTY_MAP, clientId, clientDetails.getScope(), "custom");

		//获取OAuth2Request对象

		OAuth2Request oAuth2Request = tokenRequest.createOAuth2Request(clientDetails);

		//new出一个OAuth2Authentication对象
		OAuth2Authentication oAuth2Authentication = new OAuth2Authentication(oAuth2Request, authentication);

		//生成token
		OAuth2AccessToken token = authorizationServerTokenServices.createAccessToken(oAuth2Authentication);

		if(authentication instanceof AnonymousToken){
			log.debug("游客登录逻辑————————————");
			//TODO　构建个人信息（userInfo）和权限（权限暂时不需要）
			SocialUser socialUser = (SocialUser) authentication.getPrincipal();
			String userId = socialUser.getUsername();
			TYhxx tYhxx = MessageQueue.ANONYMOUS_MAP.get(userId );
			if(Objects.isNull(tYhxx)){
				throw new UsernameNotFoundException(userId+"，游客信息不内存中不存在");
			}else{
				UserInfo userInfo = new UserInfo();
				userInfo.setJh(tYhxx.getJh());
				userInfo.setYhmc(tYhxx.getXm());
				userInfo.setSfzhm(tYhxx.getSfzhm());
				userInfo.setSjhm("-1");
				userInfo.setDwbm(tYhxx.getJgbm());
				userInfo.setJgqc(tYhxx.getJgqc());
				userInfo.setZplj(tYhxx.getYhzpdz());
				MessageQueue.ANONYMOUS_MAP.remove(userId );
				redisTemplate.opsForValue().set(BaseContant.USERCODE +userInfo.getSfzhm(),userInfo,86400, TimeUnit.SECONDS);
			}
		}else{
			SocialUser socialUser = (SocialUser) authentication.getPrincipal();
			String userId = socialUser.getUsername();
			UserInfo userInfo = MessageQueue.userInfo.get(userId);
			OauthUser oauthUser = MessageQueue.userOauth.get(userId);
			userService.setCurrentUser(userInfo);
			userService.setOauth(oauthUser);
			LogDto instance = logFactory.getInstance(userInfo.getYhmc(), userInfo.getSfzhm(), userInfo.getJh(), userInfo.getDwbm(),
					userInfo.getJgqc(), LogType.LOGIN.code, HttpServletRequestUtil.getIp(request), LogLevel.OK.code,
					1, null, "登陆认证成功", "安全认证服务");
			MessageQueue.LOGQUEUE.add(instance);
		}
		//将生成的token返回
		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().write(objectMapper.writeValueAsString(token));

	}

	/**
	 * 从header获取Authentication信息 --- 》 clientId和clientSecret
	 * @param header
	 * @param request
	 * @return
	 * @throws IOException
	 */
	private String[] extractAndDecodeHeader(String header, HttpServletRequest request)
			throws IOException {

		byte[] base64Token = header.substring(6).getBytes("UTF-8");
		byte[] decoded;
		try {
			decoded = Base64.getDecoder().decode(base64Token);
		}
		catch (IllegalArgumentException e) {
			throw new BadCredentialsException(
					"Failed to decode basic authentication token");
		}

		String token = new String(decoded, "UTF-8");

		int delim = token.indexOf(":");

		if (delim == -1) {
			throw new BadCredentialsException("Invalid basic authentication token");
		}
		return new String[] { token.substring(0, delim), token.substring(delim + 1) };
	}

}
