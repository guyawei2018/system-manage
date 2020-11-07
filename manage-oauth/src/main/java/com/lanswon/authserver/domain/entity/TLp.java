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
@Table(name = "t_lp")
public class TLp {
    /**
     * 访问令牌
     */

    @Column(name = "FWLP")
    private String fwlp;

    /**
     * 手机号码
     */
    @Id
    @Column(name = "SJHM")
    private String sjhm;

    /**
     * 申请地址
     */
    @Column(name = "SQDZ")
    private String sqdz;

    /**
     * 下发时间
     */
    @Column(name = "XFSJ")
    private Date xfsj;
}