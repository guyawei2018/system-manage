package com.lanswon.ssm.service;

import com.lanswon.base.support.SimpleResponse;
import com.lanswon.ssm.domain.dto.PermissionQueryDto;
import com.lanswon.ssm.domain.entity.TQx;
import com.lanswon.ssm.exception.ApplicationException;

/**
 * @Description:
 * @Author GU-YW
 * @Date 2020/6/16 17:34
 */
public interface SysPermissionService {

    /**
     * 新增系统权限
     * @param permission
     * @return
     * @throws ApplicationException
     */
    SimpleResponse addSysPermission(TQx permission) throws ApplicationException;

    /**
     * 修改系统权限
     * @param permission
     * @return
     * @throws ApplicationException
     */
    SimpleResponse updateSysPermission(TQx permission) throws ApplicationException;

    /**
     * 条件分页查询所有系统权限
     * @param dto
     * @return
     */
    SimpleResponse queryAll(PermissionQueryDto dto);

    /**
     * 删除系统权限
     * @param permissionId
     * @return
     */
    SimpleResponse deleteSysPermission(Integer permissionId) throws ApplicationException;

//    /**
//     * 查询空白权限树
//     * @return
//     */
//    SimpleResponse queryTree();

}
