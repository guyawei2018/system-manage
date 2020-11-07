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
@Table(name = "t_zp")
public class TZp {
    /**
     * 身份证号码
     */
    @Id
    @Column(name = "SFZHM")
    private String sfzhm;

    /**
     * 压缩路径
     */
    @Column(name = "YSLJ")
    private String yslj;

    /**
     * 路径
     */
    @Column(name = "LJ")
    private String lj;

    /**
     * 照片名称
     */
    @Column(name = "ZPMC")
    private String zpmc;

    /**
     * 照片64码
     */
    @Column(name = "ZP64M")
    private String zp64m;
}