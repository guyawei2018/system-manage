package com.lanswon.ssm.vo;


import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName UserInfo
 * @Description 当前用户信息
 * @Author zsw
 * @Date 2020/6/28 15:27
 * @Version V1.0
 **/
@Data
public class UserInfo implements Serializable {

    /**
     * 主键id
     */
    private Integer yhbh;

    /**
     * 用户名称
     */
    private String yhmc;

    private String yhmm;

    /**
     * 警号
     */
    private String jh;

    /**
     * 身份证号码
     */
    private String sfzhm;

    private String yhzt;

    /**
     * 手机号码
     */
    private String sjhm;

    /**
     * 所属组织编码
     */
    private String dwbm;

    /**
     * 机构全称
     */
    private String jgqc;

    /**
     * 工作照片路径
     */
    private String zplj;

    /**
     * 数据层级
     */
    private String sjdj;


}