/**
 * 
 */
package com.lanswon.authcore.validatecode;

import org.springframework.web.context.request.ServletWebRequest;

/** 验证码生成器,以便其他项目扩展(以增量的方式来实现变化)
 * @author GU-YW
 *
 */
public interface ValidateCodeGenerator {

	ValidateCode validateCodeGenerate(ServletWebRequest request);
	
}
