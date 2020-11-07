package com.lanswon.authapp.config;

import com.lanswon.authcore.properties.OAuth2ClientProperties;
import com.lanswon.authcore.properties.SecurityProperties;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.builders.InMemoryClientDetailsServiceBuilder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/** 认证服务器配置 当继承AuthorizationServerConfigurerAdapter需要手动配置endpoints、clients
 * 在yml中的配置会失效
 * @Author GU-YW
 * @Date 2019/11/8 19:21
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Resource
    private AuthenticationManager authenticationManager;

    @Resource
    private UserDetailsService userDetailsService;

    @Autowired
    private SecurityProperties securityProperties;

    @Resource
    private TokenStore tokenStore;

    @Autowired(required = false)
    private JwtAccessTokenConverter jwtAccessTokenConverter;

    @Autowired(required = false)
    private TokenEnhancer jwtTokenEnhancer;

    @Autowired
    PasswordEncoder passwordEncoder;


    /***
     * 入口点相关配置  ---  token的生成，存储方式等在这里配置
     * @param endpoints
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {

        endpoints
                //指定使用的TokenStore，tokenStore用来存取token，默认使用InMemoryTokenStore
                .tokenStore(tokenStore)
                //下面的配置主要用来指定"对正在进行授权的用户进行认证+校验"的类
                //在实现了AuthorizationServerConfigurerAdapter适配器类后，必须指定下面两项
                .authenticationManager(authenticationManager)
                .accessTokenConverter(jwtAccessTokenConverter)
                .userDetailsService(userDetailsService);

        if(Objects.nonNull(jwtAccessTokenConverter) && Objects.nonNull(jwtTokenEnhancer)){
            // 配置增强器链
            // 并利用增强器链将生成jwt的TokenEnhancer（jwtAccessTokenConverter）
            // 和我们扩展的TokenEnhancer设置到token的生成类中
            TokenEnhancerChain enhancerChain = new TokenEnhancerChain();
            List<TokenEnhancer> enhancers = new ArrayList<>();
            enhancers.add(jwtAccessTokenConverter);
            enhancers.add(jwtTokenEnhancer);
            enhancerChain.setTokenEnhancers(enhancers);
            endpoints.tokenEnhancer(enhancerChain);
//                   // .accessTokenConverter(jwtAccessTokenConverter);


        }
    }

    /***
     * 第三方客户端相关的配置在这里进行配置 ,之前我们在yml配置文件里对客户端进行过简单的配置
     * 在这里进行配置会覆盖yml中的配置
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        InMemoryClientDetailsServiceBuilder builder=clients.inMemory();

        if(ArrayUtils.isNotEmpty(securityProperties.getOauth2().getClients())){
            for(OAuth2ClientProperties auth2Client:securityProperties.getOauth2().getClients()){
                builder.withClient(auth2Client.getClientId())
                        .secret(passwordEncoder.encode(auth2Client.getClientSecret()))
                        .accessTokenValiditySeconds(auth2Client.getAccessTokenValiditySeconds())
                        .refreshTokenValiditySeconds(auth2Client.getRefTokenValiditySeconds())
                        .authorizedGrantTypes("refresh_token","password")
                        .scopes("all","read","write");
            }
        }

    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer authorizationServerSecurityConfigurer) throws Exception {
        authorizationServerSecurityConfigurer.checkTokenAccess("permitAll()");
    }
}
