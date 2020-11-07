/**
 * 
 */
package com.lanswon.authcore.properties;

import com.lanswon.authcore.contants.LoginResponseType;
import com.lanswon.authcore.contants.SecurityConstants;
import lombok.Data;
import lombok.ToString;

/**
 * 浏览器安全配置
 * @author GU-YW
 *
 */
@Data
@ToString
public class BrowserProperties {
	
	private SessionProperties session = new SessionProperties();
	
	private String signUpUrl = "/imooc-signUp.html";

	/**
	 * 当设置为空时会报错，应该是路径，不是路径则直接返回JSON
	 */
	private String signOutSuccessUrl = "JSON" ;
	
	private String loginPage = SecurityConstants.DEFAULT_LOGIN_PAGE_URL;
	
	private LoginResponseType loginType = LoginResponseType.JSON;
	
	private int rememberMeSeconds = 3600;
	
}
