package com.lanswon.authserver.domain.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@ToString
@Table(name = "t_yhzp")
public class TYhzp {
    /**
     * 身份证号码
     */
    @Id
    @Column(name = "SFZHM")
    private String sfzhm;

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

    /**
     * 用户照片base64
     */
    @Column(name = "YHZP")
    private String yhzp;
}