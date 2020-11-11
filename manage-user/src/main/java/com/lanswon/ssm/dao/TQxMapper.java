package com.lanswon.ssm.dao;

import com.lanswon.ssm.domain.dto.PermissionQueryDto;
import com.lanswon.ssm.domain.entity.TQx;
import com.lanswon.ssm.domain.vo.MenuTreeVo;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface TQxMapper extends Mapper<TQx> {

    /**
     * 条件查询所有系统权限，可分页或者不分页
     * @param dto
     * @return
     */
    List<TQx> queryAllPermission(PermissionQueryDto dto);

    /**
     * 统计
     * @param dto
     * @return
     */
    int count(PermissionQueryDto dto);


    /**
     * 查询所有权限形成权限树
     * @return
     */
    List<MenuTreeVo> queryPermissionToTree();

    /**
     * 查询所有一级菜单
     * @return
     */
    List<MenuTreeVo> queryParentMenu();

}