package com.lanswon.authcore.validatecode.sms;

import java.util.HashMap;
import java.util.Map;

/**系统默认的实现（）
 * @author zhailiang
 *
 */
public class DefaultSmsCodeSender implements SmsCodeSender {



	@Override
	public void send(String mobile, String code) {

		System.out.println("向手机"+mobile+"发送短信验证码"+code);
		Map<String,String> json = new HashMap<>();
		json.put("destaddr",mobile);
		json.put("messagecontent","情指平台登陆验证码:"+code+",验证码有效期为180秒");
	}

}
