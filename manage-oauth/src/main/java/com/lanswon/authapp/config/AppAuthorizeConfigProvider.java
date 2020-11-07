package com.lanswon.authapp.config;

import com.lanswon.authcore.authorize.AuthorizeConfigProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.stereotype.Component;

/** App权限配置
 * @Author GU-YW
 * @Date 2019/11/14 16:51
 */
@Component
public class AppAuthorizeConfigProvider implements AuthorizeConfigProvider {

    @Override
    public boolean config(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config) {
        config.antMatchers("/social/signUp")
                .permitAll();
        return false;
    }
}
