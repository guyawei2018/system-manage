package com.lanswon.ssm.service;

import com.lanswon.base.support.SimpleResponse;
import com.lanswon.ssm.domain.dto.RolePermissionDto;
import com.lanswon.ssm.domain.dto.RoleQueryDto;
import com.lanswon.ssm.domain.entity.TJs;
import com.lanswon.ssm.exception.ApplicationException;

/**
 * @Description:
 * @Author GU-YW
 * @Date 2020/6/16 17:34
 */
public interface SysRoleService {

    /**
     * 新增系统角色
     * @param role
     * @return
     * @throws ApplicationException
     */
    SimpleResponse addSysRole(TJs role) throws ApplicationException;

    /**
     * 修改系统角色
     * @param role
     * @return
     * @throws ApplicationException
     */
    SimpleResponse updateSysRole(TJs role) throws ApplicationException;

    /**
     * 条件分页查询所有系统角色
     * @param dto
     * @return
     */
    SimpleResponse queryAll(RoleQueryDto dto);

    /**
     * 删除系统角色
     * @param roleId
     * @return
     */
    SimpleResponse deleteSysRole(Integer roleId) throws ApplicationException;

    /**
     *  查询当前角色已拥有的权限
     * @param roleId
     * @return
     */
    SimpleResponse selectSysRolePermission(Integer roleId) throws ApplicationException;


    /**
     * 角色绑定权限
     * @param dto
     * @return
     */
    SimpleResponse bindingPermission(RolePermissionDto dto) throws ApplicationException;



}
