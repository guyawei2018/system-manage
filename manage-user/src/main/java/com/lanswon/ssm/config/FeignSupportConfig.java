package com.lanswon.ssm.config;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixCommandProperties;
import feign.Feign;
import feign.Target;
import feign.hystrix.SetterFactory;
import org.springframework.context.annotation.Bean;

import java.lang.reflect.Method;

/**
 * @Description:
 * @Author: GU-YW
 * @Date: 2020/8/11 15:59
 */
//@Configuration
public class FeignSupportConfig {

    @Bean
    public SetterFactory setterFactory(){
        SetterFactory setterFactory = new SetterFactory() {
            @Override
            public HystrixCommand.Setter create(Target<?> target, Method method) {
                String groupkey = target.name();
                String  commandkey = Feign.configKey(target.type(),method);
                HystrixCommandProperties.Setter setter = HystrixCommandProperties.Setter()
                        //TODO 设置统计指针60秒一个时间窗口
                        .withMetricsRollingStatisticalWindowInMilliseconds(1000 * 60)
                        //TODO 超过80%失败率
                        .withCircuitBreakerErrorThresholdPercentage(80)
                        //TODO 操作5个开启短路器
                        .withCircuitBreakerRequestVolumeThreshold(5)
                        //TODO 设置线程隔离
                        .withExecutionIsolationStrategy(HystrixCommandProperties.ExecutionIsolationStrategy.SEMAPHORE)
                        //TODO 设置路由的开启时间60秒
                        .withCircuitBreakerSleepWindowInMilliseconds(1000 * 60);

                return HystrixCommand.Setter
                        .withGroupKey(HystrixCommandGroupKey.Factory.asKey(groupkey))
                        .andCommandKey(HystrixCommandKey.Factory.asKey(commandkey))
                        .andCommandPropertiesDefaults(setter);

            }
        };
        return setterFactory;
    }
}
