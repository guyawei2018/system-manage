package com.lanswon.authcore.validatecode;

import com.lanswon.authcore.contants.ValidateCodeType;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * @author : GU-YW
 * @date : 2019/1q/10 21:55
 * Description：保存、获取、移除验证码的模版接口
 */
public interface ValidateCodeRepository {

    /**
     * 保存验证码
     * @param request
     * @param code
     * @param validateCodeType
     */
    void save(ServletWebRequest request, ValidateCode code, ValidateCodeType validateCodeType);
    /**
     * 获取验证码
     * @param request
     * @param validateCodeType
     * @return
     */
    ValidateCode get(ServletWebRequest request, ValidateCodeType validateCodeType);
    /**
     * 移除验证码
     * @param request
     * @param codeType
     */
    void remove(ServletWebRequest request, ValidateCodeType codeType);
}
