package com.lanswon.ssm.dao;

import com.lanswon.ssm.domain.vo.UserRoleVo;
import com.lanswon.ssm.vo.OauthUrl;
import com.lanswon.ssm.vo.UserInfo;
import com.lanswon.ssm.domain.dto.UserQueryDto;
import com.lanswon.ssm.domain.entity.TYh;
import com.lanswon.ssm.domain.vo.UserVo;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface TYhMapper extends Mapper<TYh> {

    /**
     * 条件查询所有系统用户，可分页或者不分页
     * @param dto
     * @return
     */
    List<UserVo> queryAllUsers(UserQueryDto dto);

    /**
     * 统计
     * @param dto
     * @return
     */
    int count(UserQueryDto dto);

    /**
     * 查询用户信息
     * @param param
     * @return
     */
    UserInfo queryUserInfo(@Param("param") String param);

    /**
     * 查询用户权限
     * @param idNo
     * @param permissionType
     * @return
     */
    List<OauthUrl> queryUserPermission(@Param("idNo")String idNo, @Param("permissionType") String permissionType);

    /**
     * 查询用户角色信息
     * @param yhbh
     * @return
     */
    List<UserRoleVo> queryUserRole(@Param("yhbh") Integer yhbh);
}