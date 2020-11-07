package com.lanswon.ssm.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lanswon.base.support.SimpleResponse;
import com.lanswon.ssm.domain.dto.UserQueryDto;
import com.lanswon.ssm.domain.dto.UserDto;
import com.lanswon.ssm.domain.dto.UserRoleDto;
import com.lanswon.ssm.exception.ApplicationException;

import java.io.IOException;
import java.io.UnsupportedEncodingException;


/**
 * @Description:
 * @Author GU-YW
 * @Date 2020/6/16 10:08
 */
public interface SysUserService {

    /**
     * 新增系统用户
     * @param usrDto
     * @return
     * @throws ApplicationException
     */
    SimpleResponse addSysUser(UserDto usrDto) throws ApplicationException, JsonProcessingException;

    /**
     * 上传图片
     * @param usrDto
     * @return
     */
    SimpleResponse upload(UserDto usrDto) throws ApplicationException, IOException;

    /**
     * 修改系统用户
     * @param usrDto
     * @return
     * @throws ApplicationException
     */
    SimpleResponse updateSysUser(UserDto usrDto) throws ApplicationException, JsonProcessingException;

    /**
     * 条件分页查询所有系统用户
     * @param dto
     * @return
     */
    SimpleResponse queryAll(UserQueryDto dto);


    /**
     * 查询用户已拥有的角色
     * @param userId
     * @return
     * @throws ApplicationException
     */
    SimpleResponse selectUserRole(Integer userId) throws ApplicationException;

    /**
     * 用户角色绑定
     * @param dto
     * @return
     * @throws ApplicationException
     */
    SimpleResponse userRole(UserRoleDto dto) throws ApplicationException;

    /**
     * 查询用户信息
     * @param param 手机号码/身份证号码
     * @return
     */
    SimpleResponse queryUserInfo(String param) throws ApplicationException;

    /**
     * 当前用户查询
     * @param param
     * @return
     */
    SimpleResponse me(String param) throws UnsupportedEncodingException;





}
