/**
 * 
 */
package com.lanswon.authcore.support;

import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

/**
 * @author GU-YW
 *
 */
@Slf4j
@Data
@Builder
public class SimpleResponse<T> {

	/**
	 * 返回状态码
	 */
	private int code;

	/**
	 * 返回描述信息
	 */
	private String msg;

	/**
	 * 返回数据
	 */
	private T data;

	public static SimpleResponse ok(String msg){
		return SimpleResponse.builder()
				.code(HttpStatus.OK.value())
				.msg(msg)
				.build();
	}

	public static SimpleResponse ok(Object object){
		return SimpleResponse.builder()
				.code(HttpStatus.OK.value())
				.data(object)
				.build();
	}
}
