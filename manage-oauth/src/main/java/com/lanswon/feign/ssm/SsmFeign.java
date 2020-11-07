package com.lanswon.feign.ssm;

import com.lanswon.base.support.SimpleResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Description:
 * @Author GU-YW
 * @Date 2020/6/18 14:32
 */
@FeignClient(value = "swat-sysmanage",fallbackFactory = SsmFeignFallback.class)
public interface SsmFeign {

    /**
     * 查询个人信息
     * @param data
     * @return
     */
    @ResponseBody
    @GetMapping("/feign/user/one")
    SimpleResponse queryOne(@RequestParam("data") String data);
}
