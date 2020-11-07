package com.lanswon.gateway.config;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import reactor.core.publisher.Mono;

/**
 * @Description:
 * @Author: GU-YW
 * @Date: 2020/7/15 17:59
 */
@Configuration
public class BeanConfig {


    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }


    @Bean
    public KeyResolver pathKeyResolver(){
        return exchange -> Mono.just(
                exchange.getRequest()
                .getPath()
                .toString()
        );
    }

}
