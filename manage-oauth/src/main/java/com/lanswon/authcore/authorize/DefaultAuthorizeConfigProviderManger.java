package com.lanswon.authcore.authorize;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.stereotype.Component;

import java.util.List;

/** 管理  AuthorizeConfigProvider
 * @Author GU-YW
 * @Date 2019/11/14 16:28
 */
@Component
public class DefaultAuthorizeConfigProviderManger implements AuthorizeConfigProviderManger {


    @Autowired
    private List<AuthorizeConfigProvider> authorizeConfigProviders;

    @Override
    public void config(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config) throws Exception {
        boolean existAnyRequestConfig = false;
        String existAnyRequestConfigName = null;

        for(AuthorizeConfigProvider authorizeConfigProvider:authorizeConfigProviders){
            boolean currentIsAnyRequestConfig = authorizeConfigProvider.config(config);
            if (existAnyRequestConfig && currentIsAnyRequestConfig) {
                throw new RuntimeException("重复的anyRequest配置:" + existAnyRequestConfigName + ","
                        + authorizeConfigProvider.getClass().getSimpleName());
            } else if (currentIsAnyRequestConfig) {
                existAnyRequestConfig = true;
                existAnyRequestConfigName = authorizeConfigProvider.getClass().getSimpleName();
            }
        }
        if(!existAnyRequestConfig){
            config.anyRequest().authenticated();
        }
    }
}
