package com.lanswon.authcore.provider;

import com.lanswon.authcore.token.DigitalCertToken;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * 提供执行数字证书登录的provider 类似表单认证中的AbstractUserDetailsAuthenticationProvider
 * @Author GU-YW
 * @Date 2019/10/31 20:38
 * Description：
 */
public class DigitalCertAuthenticationProvider implements AuthenticationProvider {

    private UserDetailsService userDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        DigitalCertToken authenticationToken = (DigitalCertToken) authentication;
        UserDetails user = userDetailsService.loadUserByUsername((String) authenticationToken.getPrincipal());

        if (user == null) {
            throw new InternalAuthenticationServiceException("数字证书登录无法获取用户信息");
        }
        if(user.isEnabled() == false){
            throw new DisabledException("用户未审核");
        }


        DigitalCertToken authenticationResult = new DigitalCertToken(user, user.getAuthorities());

        authenticationResult.setDetails(authenticationToken.getDetails());

        return authenticationResult;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        //参考源码写
        return DigitalCertToken.class.isAssignableFrom(authentication);
    }

    public UserDetailsService getUserDetailsService() {
        return userDetailsService;
    }

    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }
}
