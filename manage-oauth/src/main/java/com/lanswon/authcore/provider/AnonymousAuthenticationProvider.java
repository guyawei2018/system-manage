package com.lanswon.authcore.provider;

import com.lanswon.authcore.token.AnonymousToken;
import com.lanswon.authserver.security.CustomUserDetailsService;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * 提供执行游客登录的provider 类似表单认证中的AbstractUserDetailsAuthenticationProvider
 * @Author GU-YW
 * @Date 2019/10/31 20:38
 * Description：
 */
public class AnonymousAuthenticationProvider implements AuthenticationProvider {

    private CustomUserDetailsService userDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        AnonymousToken authenticationToken = (AnonymousToken) authentication;
        UserDetails user = userDetailsService.loadUserByAnonymous((String) authenticationToken.getPrincipal());

//        if (user == null) {
//            throw new InternalAuthenticationServiceException("数字证书登录无法获取用户信息");
//        }
//        if(user.isEnabled() == false){
//            throw new DisabledException("用户未审核");
//        }

        AnonymousToken authenticationResult = new AnonymousToken(user, user.getAuthorities());

        authenticationResult.setDetails(authenticationToken.getDetails());

        return authenticationResult;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        //参考源码写
        return AnonymousToken.class.isAssignableFrom(authentication);
    }

    public UserDetailsService getUserDetailsService() {
        return userDetailsService;
    }

    public void setUserDetailsService(CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }
}
