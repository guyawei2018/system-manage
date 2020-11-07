package com.lanswon.authapp.config;

import com.lanswon.authcore.authorize.AuthorizeConfigProviderManger;
import com.lanswon.authcore.config.CustomAuthenticationSecurityConfig;
import com.lanswon.authcore.config.ValidateCodeSecurityConfig;
import com.lanswon.authcore.contants.SecurityConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.expression.OAuth2WebSecurityExpressionHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.annotation.Resource;

/** 资源服务器配置
 * @Author GU-YW
 * @Date 2019/11/11 10:32
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {


    @Autowired
    private AuthenticationFailureHandler failureHandler;

    @Autowired
    private AuthenticationSuccessHandler successHandler;

    @Resource
    private ValidateCodeSecurityConfig validateCodeSecurityConfig;


    @Resource
    private CustomAuthenticationSecurityConfig customAuthenticationSecurityConfig;

    @Resource
    private AuthorizeConfigProviderManger authorizeConfigProviderManger;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.formLogin()
                .loginPage(SecurityConstants.DEFAULT_UNAUTHENTICATION_URL)
                .loginProcessingUrl(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_FORM)
                .successHandler(successHandler)
                .failureHandler(failureHandler)
                .and()
                //将验证码校验逻辑放开
                .apply(validateCodeSecurityConfig)
                .and()
                .apply(customAuthenticationSecurityConfig)
                .and()
                .csrf()
                .disable();
        authorizeConfigProviderManger.config(http.authorizeRequests());
    }

    @Autowired
    private OAuth2WebSecurityExpressionHandler expressionHandler;

    @Bean
    public OAuth2WebSecurityExpressionHandler oAuth2WebSecurityExpressionHandler(ApplicationContext applicationContext) {
        OAuth2WebSecurityExpressionHandler expressionHandler = new OAuth2WebSecurityExpressionHandler();
        expressionHandler.setApplicationContext(applicationContext);
        return expressionHandler;
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.expressionHandler(expressionHandler);
    }
}
