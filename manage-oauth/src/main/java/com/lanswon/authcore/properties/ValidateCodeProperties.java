/**
 * 
 */
package com.lanswon.authcore.properties;

import lombok.Data;
import lombok.ToString;

/**
 * 验证码配置
 * @author GU-YW
 *
 */
@Data
@ToString
public class ValidateCodeProperties {
	
	private ImageCodeProperties image = new ImageCodeProperties();
	
	private SmsCodeProperties sms = new SmsCodeProperties();

	
}
