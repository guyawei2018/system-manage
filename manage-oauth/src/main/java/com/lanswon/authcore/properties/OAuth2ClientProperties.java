package com.lanswon.authcore.properties;

import lombok.Data;

/**
 * @Author GU-YW
 * @Date 2019/11/12 20:01
 */
@Data
public class OAuth2ClientProperties {

    private String clientId;

    private String clientSecret;

    private int accessTokenValiditySeconds = 7200;

    private int refTokenValiditySeconds = 7200;
}
