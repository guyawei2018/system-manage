package com.lanswon.authcore.contants;

import com.lanswon.authserver.domain.entity.TLp;
import com.lanswon.authserver.domain.entity.TYhxx;
import com.lanswon.base.log.LogDto;
import com.lanswon.ssm.vo.OauthUser;
import com.lanswon.ssm.vo.UserInfo;

import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * @Description: 消息队列
 * @Author: GU-YW
 * @Date: 2020/7/1 14:09
 */
public class MessageQueue {

    public static Queue<TLp> activeQueue = new LinkedBlockingDeque<>();

    /**
     * 日志队列
     */
    public static Queue<LogDto> LOGQUEUE = new LinkedBlockingDeque<>();

    /**
     * 个人信息--待验证的个人信息
     */
    public static Map<String, UserInfo> userInfo = new HashMap<>();

    /**
     * 个人信息--待验证的个人信息
     */
    public static Map<String, OauthUser> userOauth = new HashMap<>();


    /**
     * 个人信息--游客登录个人信息
     */
    public static Map<String, TYhxx> ANONYMOUS_MAP = new HashMap<>();


}
