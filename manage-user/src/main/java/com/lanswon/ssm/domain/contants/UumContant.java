package com.lanswon.ssm.domain.contants;

import com.lanswon.base.log.LogDto;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * @Description: 用户管理常量
 * @Author: GU-YW
 * @Date: 2020/6/29 14:19
 */
public class UumContant {

    /**
     * 手机验证码key前缀
     */
    public final static String SMSCODE = "code:sms:";

    /**
     * 权限key前缀
     */
    public final static String OAUTHCODE ="oauth:";

    /**
     * 个人信息key前缀
     */
    public final static String USERCODE ="user:";

    /**
     * 工作角色启用
     */
    public final static  String ACTIVE_ROLE_WORK = "1";

    /**
     * 工作角色停用
     */
    public final static  String DEACTIVE_ROLE_WORK = "0";

    /**
     * 权限类型 菜单
     */
    public final static String PERMISSION_TYPE_MENU= "0";

    /**
     * 权限类型 按钮
     */
    public final static String PERMISSION_TYPE_BUTTON= "1";

    /**
     * 权限类型 无按钮类
     */
    public final static String PERMISSION_TYPE_OTHER= "2";

    /**
     * 日志队列
     */
    public static Queue<LogDto> LOGQUEUE = new LinkedBlockingDeque<>();








}
