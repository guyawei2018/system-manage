/**
 * 
 */
package com.lanswon.authcore.properties;

import lombok.Data;
import lombok.ToString;

/**
 * 验证码图片配置
 * @author GU-YW
 *
 */
@Data
@ToString
public class ImageCodeProperties extends SmsCodeProperties {
	
	public ImageCodeProperties() {
		setLength(4);
	}
	 
	private int width = 67;

	private int height = 23;


}
