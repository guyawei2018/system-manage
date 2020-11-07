package com.lanswon.authapp.config;

import com.lanswon.authcore.properties.SecurityProperties;
import com.lanswon.authapp.bean.JwtTokenEnhancer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

/**
 * @Author GU-YW
 * @Date 2019/11/12 20:48
 * Description: TokenStore的实现类有5个，可以在yml里通过配置指定使用RedisTokenStore还是JwtTokenStore
 */
@Configuration
public class TokenStoreConfig {

    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

    /***
     * 将RedisTokenStore注入到spring容器
     * 当yml配置文件里配置了nrsc.security.oauth2.tokenStore = redis时 ---> 下面的配置生效
     * @return
     */
    @Bean
    @ConditionalOnProperty(prefix = "lanswon.security.oauth2", name = "tokenStore", havingValue = "redis")
    public TokenStore redisTokenStore(){
        return new RedisTokenStore(redisConnectionFactory);
    }

    @Bean
    @ConditionalOnProperty(prefix = "lanswon.security.oauth2", name = "tokenStore", havingValue = "inMemory")
    public TokenStore inMemoryTokenStore(){
        return new InMemoryTokenStore();
    }

    /***
     * 当yml配置文件里配置了nrsc.security.oauth2.tokenStore = jwt或者(matchIfMissing = true)根本就没配置该属性时 ---> 下面的配置生效
     */
    @Configuration
    @ConditionalOnProperty(prefix = "lanswon.security.oauth2", name = "tokenStore", havingValue = "jwt", matchIfMissing = true)
    public static class JwtConfig {

        @Autowired
        private SecurityProperties securityProperties;

        /***
         * 配置JwtTokenStore ---> TokenStore只负责token的存储，不负责token的生成
         * @return
         */
        @Bean
        public TokenStore jwtTokenStore() {
            return new JwtTokenStore(jwtAccessTokenConverter());
        }

        /***
         * JwtAccessTokenConverter 其实就是一个TokenEnhancer
         * 其实JwtAccessTokenConverter就是将默认生成的token做进一步处理使其成为一个JWT
         * @return
         */
        @Bean
        public JwtAccessTokenConverter jwtAccessTokenConverter() {
            JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
            //加入密签 --- 一定要保护好
            converter.setSigningKey(securityProperties.getOauth2().getJwtSigningKey());
            return converter;
        }

        /***
         * 将自定义的TokenEnhancer注入到spring容器 --- 》可以覆盖该bean，实现自己的需求
         * @return
         */
        @Bean
        @ConditionalOnBean(TokenEnhancer.class)
        public TokenEnhancer jwtTokenEnhancer() {
            return new JwtTokenEnhancer();
        }
    }
}
