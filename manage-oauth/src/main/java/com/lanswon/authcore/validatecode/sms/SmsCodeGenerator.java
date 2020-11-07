package com.lanswon.authcore.validatecode.sms;

import com.lanswon.authcore.validatecode.ValidateCode;
import com.lanswon.authcore.properties.SecurityProperties;
import com.lanswon.authcore.validatecode.ValidateCodeGenerator;
import lombok.Data;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * @author GU-YW
 *
 */
@Data
public class SmsCodeGenerator implements ValidateCodeGenerator {

	private SecurityProperties securityProperties;

	@Override
	public ValidateCode validateCodeGenerate(ServletWebRequest request) {
		String code = RandomStringUtils.randomNumeric(securityProperties.getCode().getSms().getLength());
		return new ValidateCode(code, securityProperties.getCode().getSms().getExpireIn());
	}

	
	

}
