package com.lanswon.ssm.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lanswon.base.support.JWTKey;
import com.lanswon.base.support.SimpleResponse;
import com.lanswon.ssm.domain.dto.MenuDto;
import com.lanswon.ssm.domain.dto.UserDto;
import com.lanswon.ssm.domain.dto.UserQueryDto;
import com.lanswon.ssm.domain.dto.UserRoleDto;
import com.lanswon.ssm.exception.ApplicationException;
import com.lanswon.ssm.service.SysUserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * @Description:
 * @Author: GU-YW
 * @Date: 2020/11/4 15:09
 */
@RestController
@RequestMapping("/user")
@Api(tags="用户管理")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserController {

    private final SysUserService userService;

    @PostMapping("/list")
    @ApiOperation(value="查询用户信息列表")
    public SimpleResponse queryAll(@RequestBody UserQueryDto userDto) {
        return userService.queryAll(userDto);
    }

    @PostMapping("/add")
    @ApiOperation(value="新增用户信息")
    public SimpleResponse addSysUser(@RequestBody UserDto userDto) throws JsonProcessingException, ApplicationException {
        return userService.addSysUser(userDto);
    }

    @PostMapping("/upload")
    @ApiOperation(value="新增用户头像上传")
    public SimpleResponse upload(@RequestBody UserDto userDto) throws IOException, ApplicationException {
        return userService.upload(userDto);
    }

    @PutMapping("/update")
    @ApiOperation(value="更新用户信息")
    public SimpleResponse updateSysUser(@RequestBody UserDto userDto) throws JsonProcessingException, ApplicationException {
        return userService.updateSysUser(userDto);
    }

    @GetMapping("/{userId}")
    @ApiOperation(value="查询用户已拥有的角色")
    public SimpleResponse queryAll(@PathVariable Integer userId) throws ApplicationException {
        return userService.selectUserRole(userId);
    }

    @PostMapping("/role/handle")
    @ApiOperation(value="新增用户角色")
    public SimpleResponse userRole(@RequestBody UserRoleDto dto) throws ApplicationException {
        return userService.userRole(dto);
    }

    @GetMapping("/me")
    @ApiOperation(value="查询当前用户")
    public SimpleResponse me(@RequestHeader(value = "token") String token) throws UnsupportedEncodingException {
        Claims claims = (Claims) Jwts.parser().setSigningKey(JWTKey.key.getBytes("UTF-8")).parseClaimsJws(token).getBody();
        String userId = (String) claims.get("user_name");
        return userService.me(userId);
    }

    @PostMapping("/menu/order")
    @ApiOperation(value="新增用户菜单排序")
    public SimpleResponse saveMenuOrderByUser(@RequestBody MenuDto dto) throws ApplicationException {
        return userService.saveMenuOrderByUser(dto);
    }
}
