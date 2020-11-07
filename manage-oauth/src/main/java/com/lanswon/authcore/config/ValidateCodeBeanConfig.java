/**
 * 
 */
package com.lanswon.authcore.config;

import com.lanswon.authcore.properties.SecurityProperties;
import com.lanswon.authcore.validatecode.ValidateCodeGenerator;
import com.lanswon.authcore.validatecode.image.ImageCodeGenerator;
import com.lanswon.authcore.validatecode.sms.DefaultSmsCodeSender;
import com.lanswon.authcore.validatecode.sms.SmsCodeGenerator;
import com.lanswon.authcore.validatecode.sms.SmsCodeSender;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author GU-YW
 *
 */
@Configuration
@EnableConfigurationProperties(SecurityProperties.class)
public class ValidateCodeBeanConfig {

	
	@Bean(name = "imageValidateCodeGenerator")
	@ConditionalOnMissingBean(name="imageValidateCodeGenerator")
	public ValidateCodeGenerator imageValidateCodeGenerator(SecurityProperties securityProperties) {
		ImageCodeGenerator codeGenerator = new ImageCodeGenerator();
		codeGenerator.setSecurityProperties(securityProperties);
		return codeGenerator;
	}

	@Bean(name = "smsValidateCodeGenerator")
	@ConditionalOnMissingBean(name = "smsValidateCodeGenerator")
	public ValidateCodeGenerator smsValidateCodeGenerator(SecurityProperties securityProperties) {
		SmsCodeGenerator smsCodeGenerator = new SmsCodeGenerator();
		smsCodeGenerator.setSecurityProperties(securityProperties);
		return smsCodeGenerator;
	}
	
	@Bean
	@ConditionalOnMissingBean
	public SmsCodeSender smsCodeSender() {
		return new DefaultSmsCodeSender();
	}


	@Bean
	public PasswordEncoder passwordEncoder(){
		return  new BCryptPasswordEncoder();
	}

}
