package com.lanswon.ssm.controller;

import com.lanswon.base.support.SimpleResponse;
import com.lanswon.ssm.domain.dto.PermissionQueryDto;
import com.lanswon.ssm.domain.entity.TQx;
import com.lanswon.ssm.exception.ApplicationException;
import com.lanswon.ssm.service.SysPermissionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Description:
 * @Author: GU-YW
 * @Date: 2020/11/4 15:09
 */
@RestController
@RequestMapping("/permission")
@Api(tags="权限管理")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PermissionController {

    private final SysPermissionService permissionService;

    @PostMapping("/list")
    @ApiOperation(value="查询权限信息列表")
    public SimpleResponse queryAll(@RequestBody PermissionQueryDto dto) {
        return permissionService.queryAll(dto);
    }

    @PostMapping("/add")
    @ApiOperation(value="新增权限信息")
    public SimpleResponse addSysPermission(@RequestBody TQx qx) throws ApplicationException {
        return permissionService.addSysPermission(qx);
    }


    @PutMapping("/update")
    @ApiOperation(value="更新权限信息")
    public SimpleResponse updateSysPermission(@RequestBody TQx qx) throws ApplicationException {
        return permissionService.updateSysPermission(qx);
    }

    @DeleteMapping("/{permissionId}")
    @ApiOperation(value="删除权限信息")
    public SimpleResponse deleteSysPermission(@PathVariable Integer permissionId) throws ApplicationException {
        return permissionService.deleteSysPermission(permissionId);
    }

}
