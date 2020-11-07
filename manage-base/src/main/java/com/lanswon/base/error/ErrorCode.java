package com.lanswon.base.error;

/**
 * @Description: 全局统一异常代码
 * @Author: GU-YW
 * @Date: 2020/6/23 11:53
 */
public enum ErrorCode {

    /////////////////////////系统管理模块异常编码////////////////////////////////////////////////

    /**
     * 系统异常
     */
    SSM_SYSTEM_EXCEPTION(2000,"系统异常"),

    /**
     * 用户不存在
     */
    SSM_USER_NO_EXITS (2001,"用户不存在,请注册"),

    /**
     * 用户已经存在
     */
    SSM_USER_HAS_EXITS (2002,"用户已经存在"),

    /**
     * 人脸注册失败
     */
    SSM_FACE_ADD_FAIL(2003,"人脸注册失败"),

    /**
     * 人脸更新失败
     */
    SSM_FACE_UPDATE_FAIL(2004,"人脸更新失败"),

    /**
     * 系统角色不存在
     */
    SSM_SYS_ROLE_NO_EXITS(2005,"系统角色不存在"),

    /**
     * 系统角色已经存在
     */
    SSM_SYS_ROLE_HAS_EXITS(2006,"系统角色已经存在"),

    /**
     * 工作角色不存在
     */
    SSM_WORK_ROLE_NO_EXITS(2007,"工作角色不存在"),

    /**
     * 工作角色已经存在
     */
    SSM_WORK_ROLE_HAS_EXITS(2008,"工作角色已经存在"),

    /**
     * 系统权限不存在
     */
    SSM_PERMISSION_NO_EXITS(2009,"系统权限不存在"),

    /**
     * 系统权限已经存在
     */
    SSM_PERMISSION_HAS_EXITS(2010,"系统权限已经存在"),

    /**
     * 用户注册验证码不存在
     */
    SSM_SMS_CODE_NO_EXITS(2011,"用户注册验证码不存在"),

    /**
     * 当前用户不存在此工作角色
     */
    SSM_USR_NO_EXITS_WORK_ROLE(2012,"当前用户不存在此工作角色"),

    /**
     * 单位不存在
     */
    SSM_DW_NO_EXITS (2013,"单位不存在"),

    /**
     * 单位已经存在
     */
    SSM_DW_HAS_EXITS (2014,"单位已经存在"),


    /**
     * 当前用户无此绑定工作角色,无法解绑
     */
    SSM_USER_HAS_NO_ROLE (2015,"当前用户无此绑定工作角色,无法解绑"),

    /**
     * 当前用户已有此工作角色
     */
    SSM_USER_HAS_ROLE (2016,"当前用户已有此工作角色"),

    /**
     * 当前模块已存在
     */
    SSM_MOUDLE_HAS_EXITS (2017,"当前模块已存在"),

    /**
     * 当前模块不存在
     */
    SSM_MOUDLE_NO_EXITS (2018,"当前模块不存在"),

    /**
     * 当前模块已被绑定使用,无法删除
     */
    SSM_MOUDLE_HAS_BINDING (2019,"当前模块已被绑定使用,无法删除"),

    /**
     * 当前注册单位已被绑定使用,无法删除
     */
    SSM_DW_HAS_BINDING (2020,"当前注册单位已被绑定使用,无法删除"),

    /**
     * 当前权限已被绑定使用，无法删除
     */
    SSM_PERMISSION_HAS_BINDING(2021,"当前权限已被绑定使用，无法删除"),

    /**
     * 当前角色已被绑定使用，无法删除
     */
    SSM_ROLE_HAS_BINDING(2022,"当前角色已被绑定使用，无法删除"),

    /**
     * 当前权限模板已存在
     */
    SSM_GROUP_HAS_EXITS(2023,"当前权限模板已存在"),

    /**
     * 当前权限模板不存在
     */
    SSM_GROUP_NO_EXITS(2024,"当前权限模板不存在"),

    /**
     * 当前权限分组存在绑定无法删除
     */
    SSM_PERMISSION_GROUP_HAS_BINDING(2025,"当前权限分组存在绑定无法删除"),


    /////////////////////////认证服务异常编码////////////////////////////////////////////////


    OTHER(0,"");


    public int code;

    public String desc;

    ErrorCode(int code, String desc){
        this.code=code;
        this.desc=desc;
    }

    public static ErrorCode getInstance(int code){
        ErrorCode enable=null;
        for(ErrorCode status:values()){
            if(status.code == code){
                enable=status;
                break;
            }
        }
        return enable;
    }
}
