package com.lanswon.authcore.validatecode.sms;

/**
 * 发送手机验证码
 * @author GU-YW
 *
 */
public interface SmsCodeSender {
	
	void send(String mobile, String code);

}
