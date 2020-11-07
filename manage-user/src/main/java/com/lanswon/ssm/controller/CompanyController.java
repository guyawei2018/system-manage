package com.lanswon.ssm.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lanswon.base.support.JWTKey;
import com.lanswon.base.support.SimpleResponse;
import com.lanswon.ssm.domain.dto.CompanyDto;
import com.lanswon.ssm.domain.dto.UserDto;
import com.lanswon.ssm.domain.dto.UserQueryDto;
import com.lanswon.ssm.domain.dto.UserRoleDto;
import com.lanswon.ssm.exception.ApplicationException;
import com.lanswon.ssm.service.SysCompanyService;
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
@RequestMapping("/company")
@Api(tags="组织管理")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CompanyController {

    private final SysCompanyService companyService;

    @PostMapping("/list")
    @ApiOperation(value="查询组织信息列表")
    public SimpleResponse queryAll(@RequestBody CompanyDto dto) {
        return companyService.queryAll(dto);
    }

}
