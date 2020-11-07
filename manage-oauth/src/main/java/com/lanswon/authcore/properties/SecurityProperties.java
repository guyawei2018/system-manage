/**
 * 
 */
package com.lanswon.authcore.properties;

import lombok.Data;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 安全配置
 * @author GU-YW
 *
 */
@Data
@ConfigurationProperties(prefix = "lanswon.security")
@ToString
public class SecurityProperties {
	
	private BrowserProperties browser = new BrowserProperties();
	
	private ValidateCodeProperties code = new ValidateCodeProperties();
	
	private OAuth2Properties oauth2 = new OAuth2Properties();

}

