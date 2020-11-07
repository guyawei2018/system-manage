package com.lanswon.ssm.domain.entity;

import java.util.Date;
import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Table(name = "t_dw")
public class TDw {
    /**
     * 机构ID
     */
    @Id
    @Column(name = "JGXH")
    private Integer jgxh;

    /**
     * 上级节点ID
     */
    @Column(name = "SJJJXH")
    private Integer sjjjxh;

    /**
     * 机构编码
     */
    @Column(name = "JGBM")
    private String jgbm;

    /**
     * 原机构代码
     */
    @Column(name = "YJGDM")
    private String yjgdm;

    /**
     * 上级机构编码
     */
    @Column(name = "SJJGBM")
    private String sjjgbm;

    /**
     * 部门名称
     */
    @Column(name = "BMMC")
    private String bmmc;

    /**
     * 大平台名称
     */
    @Column(name = "DPTMC")
    private String dptmc;

    /**
     * 机构全称
     */
    @Column(name = "JGQC")
    private String jgqc;

    /**
     * 机构简称
     */
    @Column(name = "JGJC")
    private String jgjc;

    /**
     * 机构类型
     */
    @Column(name = "JGLX")
    private Integer jglx;

    /**
     * 启用日期 20180111114901+0800
     */
    @Column(name = "QYRQ")
    private Date qyrq;

    /**
     * 更新日期 20180111114901+0800
     */
    @Column(name = "GXRQ")
    private Date gxrq;

    /**
     * 机构状态
     */
    @Column(name = "JGZT")
    private Integer jgzt;

    /**
     * 排序
     */
    @Column(name = "PX")
    private String px;

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