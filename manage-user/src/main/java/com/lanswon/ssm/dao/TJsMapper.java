package com.lanswon.ssm.dao;

import com.lanswon.ssm.domain.dto.RoleQueryDto;
import com.lanswon.ssm.domain.entity.TJs;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface TJsMapper extends Mapper<TJs> {

    /**
     * 条件查询所有系统角色，可分页或者不分页
     * @param dto
     * @return
     */
    List<TJs> queryAllRoles(RoleQueryDto dto);

    /**
     * 统计
     * @param dto
     * @return
     */
    int count(RoleQueryDto dto);
}