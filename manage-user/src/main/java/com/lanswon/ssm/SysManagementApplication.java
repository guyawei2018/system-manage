package com.lanswon.ssm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * 系统启动类
 * @author GU-YW
 */

@SpringBootApplication
@MapperScan("com.lanswon.ssm.dao")
@ComponentScan("com.lanswon")
@EnableSwagger2
@EnableTransactionManagement
@EnableBinding(Source.class)
@EnableAspectJAutoProxy
public class SysManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(SysManagementApplication.class, args);
    }



}
