package com.lanswon.ssm.domain.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Getter
@Setter
@ToString
@Table(name = "t_yh")
public class TYh {
    /**
     * 主键id
     */
    @Id
    @Column(name = "YHBH")
    private Integer yhbh;

    /**
     * 用户名称
     */
    @Column(name = "YHMC")
    private String yhmc;

    /**
     * 用户密码
     */
    @Column(name = "YHMM")
    private String yhmm;

    /**
     * 性别  1-男 2-女
     */
    @Column(name = "YHXB")
    private String yhxb;

    /**
     * 身份证号码
     */
    @Column(name = "SFZHM")
    private String sfzhm;

    /**
     * 手机号码
     */
    @Column(name = "SJHM")
    private String sjhm;

    /**
     * 用户职务
     */
    @Column(name = "YHZW")
    private String yhzw;

    /**
     * 用户状态 0-未审核 1-已审核
     */
    @Column(name = "YHZT")
    private String yhzt;

    /**
     * 人员岗位
     */
    @Column(name = "RYGW")
    private String rygw;

    /**
     * 用户昵称 删除
     */
    @Column(name = "YHNC")
    private String yhnc;

    /**
     * 所属组织编码
     */
    @Column(name = "DWBM")
    private String dwbm;

    /**
     * 最后登录时间
     */
    @Column(name = "ZHDLSJ")
    private Date zhdlsj;

    /**
     * 账号状态  0-停用  1-启用
     */
    @Column(name = "ZHZT")
    private String zhzt;

    /**
     * 人脸编号
     */
    @Column(name = "RLBH")
    private String rlbh;

    /**
     * 工作组织编号
     */
    @Column(name = "SJZZBM")
    private String sjzzbm;

    /**
     * 警号
     */
    @Column(name = "JH")
    private String jh;

    /**
     * 备用手机号1
     */
    @Column(name = "BYSJHM1")
    private String bysjhm1;

    /**
     * 管理层级编码
     */
    @Column(name = "SJBM")
    private String sjbm;

    /**
     * 数据等级
     */
    @Column(name = "SJDJ")
    private String sjdj;

    /**
     * 申请时间
     */
    @Column(name = "CJSJ")
    private Date cjsj;

    /**
     * 数据来源0 -情指平台 1-视联APP
     */
    @Column(name = "SJLY")
    private String sjly;
}