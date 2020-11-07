package com.lanswon.authserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.client.RestTemplate;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * 权限服务器
 * @author GU-YW
 */

@SpringBootApplication
@ComponentScan("com.lanswon")
@MapperScan("com.lanswon.authserver.dao")
@EnableWebSecurity
@EnableFeignClients(basePackages = "com.lanswon.feign")
public class AuthServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthServerApplication.class, args);
    }

    @Bean
    public  RestTemplate restTemplate(){
        return new RestTemplate();
    }


}
