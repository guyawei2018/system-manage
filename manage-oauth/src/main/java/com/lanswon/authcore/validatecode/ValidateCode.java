/**
 * 
 */
package com.lanswon.authcore.validatecode;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;


/**
 * @author GU-YW
 *
 */
@Getter
@ToString
@Setter
public class ValidateCode implements Serializable {
	
	private String code;
	
	private LocalDateTime expireTime;
	
	public ValidateCode(String code, int expireIn){
		this.code = code;
		this.expireTime = LocalDateTime.now().plusSeconds(expireIn);
	}
	
	public ValidateCode(String code, LocalDateTime expireTime){
		this.code = code;
		this.expireTime = expireTime;
	}

	public ValidateCode(){
	}

	public boolean isExpried() {
		return LocalDateTime.now().isAfter(expireTime);
	}
}
