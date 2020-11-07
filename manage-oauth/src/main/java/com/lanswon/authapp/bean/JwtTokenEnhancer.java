package com.lanswon.authapp.bean;


import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author GU-YW
 * @Date 2019/11/13 14:07
 * Description：自定义一个TokenEnhancer，在生成JWT时加入一些扩展信息
 */
@Slf4j
public class JwtTokenEnhancer implements TokenEnhancer {
    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        //在jwt里加入一个clientId信息,加什么自己定义
        Map<String,Object> info = new HashMap<>();
        log.debug("token加入clientId=[{}]",authentication.getOAuth2Request().getClientId());
        info.put("clientId",authentication.getOAuth2Request().getClientId());

        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(info);
        return accessToken;
    }
}
