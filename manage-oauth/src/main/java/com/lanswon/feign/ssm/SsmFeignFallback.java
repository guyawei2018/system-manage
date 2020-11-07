package com.lanswon.feign.ssm;

import com.lanswon.base.support.SimpleResponse;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @Description:
 * @Author GU-YW
 * @Date 2020/6/18 14:38
 */
@Component
@Slf4j
public class SsmFeignFallback implements FallbackFactory<SsmFeign> {

    @Override
    public SsmFeign create(Throwable throwable) {
        return new SsmFeign() {
            @Override
            public SimpleResponse queryOne(String data) {
                return SimpleResponse.ok("系统管理服务熔断");
            }
        };
    }
}
