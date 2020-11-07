package com.lanswon.ssm.controller.feign;

import com.lanswon.base.support.SimpleResponse;
import com.lanswon.ssm.service.SysUserService;
import com.lanswon.ssm.exception.ApplicationException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Description:
 * @Author: GU-YW
 * @Date: 2020/11/4 15:08
 */
@RestController
@RequestMapping("/feign")
@Api(tags="仅内部调用接口")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class FeignController {

    private final SysUserService userService;

    @GetMapping("/user/one")
    @ApiOperation(value="查询用户base64头像")
    public SimpleResponse getResourceByUser(@RequestParam("data") String data) throws ApplicationException {
        return userService.queryUserInfo(data);
    }
}
