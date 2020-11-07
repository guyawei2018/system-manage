/**
 * 
 */
package com.lanswon.authcore.validatecode.impl;

import com.lanswon.authcore.contants.ValidateCodeType;
import com.lanswon.authcore.validatecode.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

import javax.annotation.Resource;
import java.util.Map;


/**
 * @author 实现ValidateCodeProcessor 抽象类
 *
 */
@Slf4j
public abstract class AbstractValidateCodeProcessor<C extends ValidateCode> implements ValidateCodeProcessor {

	/**
	 * 操作session的工具类
	 */
	private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

	/**
	 * spring 自动收集系统中所有的 {@link ValidateCodeGenerator} 接口的实现。
	 * key 是bean 名字
	 */
	@Autowired
	private Map<String, ValidateCodeGenerator> validateCodeGenerators;

	@Resource
	private ValidateCodeRepository validateCodeRepository;


	@Override
	public void create(ServletWebRequest request) throws Exception {
		C validateCode = generate(request);
		save(request, validateCode);
		send(request, validateCode);
	}

	/**
	 * 生成校验码
	 * 
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private C generate(ServletWebRequest request) {

		String type = getValidateCodeType().toString().toLowerCase();
		String generatorName = type + ValidateCodeGenerator.class.getSimpleName();
		ValidateCodeGenerator validateCodeGenerator = validateCodeGenerators.get(generatorName);
		if (validateCodeGenerator == null) {
			throw new ValidateCodeException("验证码生成器" + generatorName + "不存在");
		}
		return (C) validateCodeGenerator.validateCodeGenerate(request);
	}

	/**
	 * 保存校验码
	 * 
	 * @param request
	 * @param validateCode
	 */
	private void save(ServletWebRequest request, C validateCode) {
		//由于存放到redis里的对象必须是序列化的，---->对象里面的属性也必须是序列化的
		// 而ImageCode中的BufferedImage对象没有实现Serializable接口，即不可序列化
		// 因此不做如下处理还是会报序列化错误
		// 实际业务中我们只需要把生成的验证码和过期时间存到session（redis）里就可以了，因此完全可以按照如下的方式去做
		ValidateCode code = new ValidateCode(validateCode.getCode(), validateCode.getExpireTime());
		log.debug("保存的验证码是[{}]",code.getCode());
		//使用validateCodeRepository接口方法保存验证码
		validateCodeRepository.save(request, code, getValidateCodeType());
	}

	/**
	 * 构建验证码放入session时的key
	 * @return
	 */
	private String getSessionKey() {
		return SESSION_KEY_PREFIX + getValidateCodeType().toString().toUpperCase();
	}

	/**
	 * 发送校验码，由子类实现
	 * 
	 * @param request
	 * @param validateCode
	 * @throws Exception
	 */
	protected abstract void send(ServletWebRequest request, C validateCode) throws Exception;

	/**
	 * 根据请求的url获取校验码的类型
	 * 
	 * @param
	 * @return
	 */
	private ValidateCodeType getValidateCodeType() {

		String type = StringUtils.substringBefore(getClass().getSimpleName(), "CodeProcessor");
		log.info("当前ValidateCodeProcessor类型为[{}]",getClass().getSimpleName());
		return ValidateCodeType.valueOf(type.toUpperCase());
	}

	@SuppressWarnings("unchecked")
	@Override
	public void validate(ServletWebRequest request) {

		ValidateCodeType processorType = getValidateCodeType();

		C codeInSession = (C) validateCodeRepository.get(request, processorType);

		String codeInRequest;
		try {
			codeInRequest = ServletRequestUtils.getStringParameter(request.getRequest(),
					processorType.getParamNameOnValidate());
		} catch (ServletRequestBindingException e) {
			throw new ValidateCodeException("获取验证码的值失败");
		}

		if (StringUtils.isBlank(codeInRequest)) {
			throw new ValidateCodeException("验证码的值不能为空");
		}

		if (codeInSession == null) {
			throw new ValidateCodeException("验证码不存在");
		}

		if (codeInSession.isExpried()) {
			validateCodeRepository.remove(request, processorType);
			throw new ValidateCodeException("验证码已过期");
		}

		if (!StringUtils.equals(codeInSession.getCode(), codeInRequest)) {
			throw new ValidateCodeException("验证码不匹配");
		}

		validateCodeRepository.remove(request, processorType);
	}

}
