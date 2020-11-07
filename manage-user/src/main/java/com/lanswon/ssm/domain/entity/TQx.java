package com.lanswon.ssm.domain.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@ToString
@Table(name = "t_qx")
public class TQx {
    /**
     * 权限编号
     */
    @Id
    @Column(name = "QXBH")
    private Integer qxbh;

    /**
     * 权限名称
     */
    @Column(name = "QXMC")
    private String qxmc;

    /**
     * 权限标识
     */
    @Column(name = "QXBS")
    private String qxbs;

    /**
     * 权限路径
     */
    @Column(name = "QXLJ")
    private String qxlj;

    /**
     * 权限方法
     */
    @Column(name = "QXFF")
    private String qxff;

    /**
     * 父权限ID
     */
    @Column(name = "FJQXBH")
    private Integer fjqxbh;

    /**
     * 排序值
     */
    @Column(name = "PX")
    private Integer px;

    /**
     * 资源类型 （0菜单 1按钮）
     */
    @Column(name = "QXLX")
    private Integer qxlx;
}