package com.lanswon.authcore.config;

import com.lanswon.authcore.filter.AnonymousAuthenticationFilter;
import com.lanswon.authcore.filter.DigitalCertAuthenticationFilter;
import com.lanswon.authcore.filter.SmsCodeAuthenticationFilter;
import com.lanswon.authcore.provider.AnonymousAuthenticationProvider;
import com.lanswon.authcore.provider.DigitalCertAuthenticationProvider;
import com.lanswon.authcore.provider.SmsCodeAuthenticationProvider;
import com.lanswon.authserver.security.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;

/** 手机验证码登录配置
 * @Author GU-YW
 * @Date 2019/11/1 11:28
 * Description：校验手机验证码登录的配置类---》将校验规则等配置到spring-security过滤器链中
 */
@Configuration
public class CustomAuthenticationSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain,HttpSecurity> {

    @Autowired
    private AuthenticationSuccessHandler successHandler;

    @Autowired
    private AuthenticationFailureHandler failureHandler;

    @Resource
    private CustomUserDetailsService userDetailsService;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        //TODO 手机验证码
        SmsCodeAuthenticationFilter smsCodeAuthenticationFilter=new SmsCodeAuthenticationFilter();
        //TODO 加入ProviderManager
        smsCodeAuthenticationFilter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));
        smsCodeAuthenticationFilter.setAuthenticationSuccessHandler(successHandler);
        smsCodeAuthenticationFilter.setAuthenticationFailureHandler(failureHandler);

        SmsCodeAuthenticationProvider smsCodeAuthenticationProvider=new SmsCodeAuthenticationProvider();
        smsCodeAuthenticationProvider.setUserDetailsService(userDetailsService);

        //TODO 数字证书
        DigitalCertAuthenticationFilter digitalCertAuthenticationFilter=new DigitalCertAuthenticationFilter();
        //TODO 加入ProviderManager
        digitalCertAuthenticationFilter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));
        digitalCertAuthenticationFilter.setAuthenticationSuccessHandler(successHandler);
        digitalCertAuthenticationFilter.setAuthenticationFailureHandler(failureHandler);

        DigitalCertAuthenticationProvider digitalCertAuthenticationProvider=new DigitalCertAuthenticationProvider();
        digitalCertAuthenticationProvider.setUserDetailsService(userDetailsService);

        //TODO 游客
        AnonymousAuthenticationFilter anonymousAuthenticationFilter = new AnonymousAuthenticationFilter();
        anonymousAuthenticationFilter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));
        anonymousAuthenticationFilter.setAuthenticationSuccessHandler(successHandler);
        anonymousAuthenticationFilter.setAuthenticationFailureHandler(failureHandler);

        AnonymousAuthenticationProvider anonymousAuthenticationProvider =  new AnonymousAuthenticationProvider();
        anonymousAuthenticationProvider.setUserDetailsService(userDetailsService);


        //TODO 将自定义的provide 加入到AuthenticationManager中的provide集合中
        http.authenticationProvider(digitalCertAuthenticationProvider)
                .authenticationProvider(smsCodeAuthenticationProvider)
                .authenticationProvider(anonymousAuthenticationProvider)
                .addFilterAfter(digitalCertAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterAfter(smsCodeAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterAfter(anonymousAuthenticationFilter,UsernamePasswordAuthenticationFilter.class);


    }
}
