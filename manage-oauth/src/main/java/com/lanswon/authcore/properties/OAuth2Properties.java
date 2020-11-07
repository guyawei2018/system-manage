package com.lanswon.authcore.properties;

import lombok.Data;

/**
 * @Author GU-YW
 * @Date 2019/11/12 20:01
 */
@Data
public class OAuth2Properties {

    private OAuth2ClientProperties[] clients = {};

    /***
     *  jwt的密签 --- 发出去的令牌需要它签名，验证令牌时也用它来验
     *  ★一定要保护好，别人知道了就可以拿着它来签发你的jwt令牌了
     */
    private String jwtSigningKey = "abcdefgHIJKLMNoPQrStUvWxYz";

    /***
     * 指定使用哪个TokenStore
     */
    private String tokenStore;

}
