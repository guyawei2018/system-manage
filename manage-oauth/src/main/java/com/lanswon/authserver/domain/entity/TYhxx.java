package com.lanswon.authserver.domain.entity;

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
@Table(name = "t_yhxx")
public class TYhxx {
    /**
     * 人员序号ID
     */
    @Id
    @Column(name = "RYXH")
    private Integer ryxh;

    /**
     * 姓名
     */
    @Column(name = "XM")
    private String xm;

    /**
     * 人员状态/数据状态
     */
    @Column(name = "RYZT")
    private Integer ryzt;

    /**
     * 身份证号码
     */
    @Column(name = "SFZHM")
    private String sfzhm;

    /**
     * 性别
     */
    @Column(name = "XB")
    private Integer xb;

    /**
     * 人员身份类型
     */
    @Column(name = "RYSFLX")
    private Integer rysflx;

    /**
     * 警号
     */
    @Column(name = "JH")
    private String jh;

    /**
     * 移动手机长号
     */
    @Column(name = "YDSJCH")
    private String ydsjch;

    /**
     * 移动手机短号
     */
    @Column(name = "YDSJDH")
    private String ydsjdh;

    /**
     * 电信手机长号
     */
    @Column(name = "DXSJCH")
    private String dxsjch;

    /**
     * 电信手机短号
     */
    @Column(name = "DXSJDH")
    private String dxsjdh;

    /**
     * 其他手机号
     */
    @Column(name = "QTSJH")
    private String qtsjh;

    /**
     * 最后更新日期 20180111114901+0800
     */
    @Column(name = "ZHGXSJ")
    private Date zhgxsj;

    /**
     * 机构序号ID
     */
    @Column(name = "JGXH")
    private Integer jgxh;

    /**
     * 机构编码
     */
    @Column(name = "JGBM")
    private String jgbm;

    /**
     * 机构全称
     */
    @Column(name = "JGQC")
    private String jgqc;

    /**
     * 办公电话
     */
    @Column(name = "BGDH")
    private String bgdh;

    /**
     * 用户照片地址,非base64
     */
    @Column(name = "YHZPDZ")
    private String yhzpdz;

    /**
     * 创建时间
     */
    @Column(name = "CJSJ")
    private Date cjsj;

    /**
     * 更新时间
     */
    @Column(name = "GXSJ")
    private Date gxsj;
}