package com.lanswon.ssm.controller;

import com.lanswon.base.support.SimpleResponse;
import com.lanswon.ssm.service.SysRoleService;
import com.lanswon.ssm.domain.dto.RolePermissionDto;
import com.lanswon.ssm.domain.dto.RoleQueryDto;
import com.lanswon.ssm.domain.entity.TJs;
import com.lanswon.ssm.exception.ApplicationException;
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
@RequestMapping("/role")
@Api(tags="角色管理")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RoleController {

    private final SysRoleService roleService;

    @PostMapping("/list")
    @ApiOperation(value="查询角色信息列表")
    public SimpleResponse queryAll(@RequestBody RoleQueryDto dto) {
        return roleService.queryAll(dto);
    }

    @PostMapping("/add")
    @ApiOperation(value="新增角色信息")
    public SimpleResponse addSysRole(@RequestBody TJs js) throws ApplicationException {
        return roleService.addSysRole(js);
    }

    @PutMapping("/update")
    @ApiOperation(value="更新角色信息")
    public SimpleResponse updateSysRole(@RequestBody TJs js) throws ApplicationException {
        return roleService.updateSysRole(js);
    }

    @DeleteMapping("/{roleId}")
    @ApiOperation(value="删除角色信息")
    public SimpleResponse deleteSysRole(@PathVariable Integer roleId) throws ApplicationException {
        return roleService.deleteSysRole(roleId);
    }

    @GetMapping("/{roleId}")
    @ApiOperation(value="查询角色已拥有的权限")
    public SimpleResponse selectSysRolePermission(@PathVariable Integer roleId) throws ApplicationException {
        return roleService.selectSysRolePermission(roleId);
    }

    @PostMapping("/role/handle")
    @ApiOperation(value="新增角色权限")
    public SimpleResponse userRole(@RequestBody RolePermissionDto dto) throws ApplicationException {
        return roleService.bindingPermission(dto);
    }

}
