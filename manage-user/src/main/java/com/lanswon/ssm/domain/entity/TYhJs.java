package com.lanswon.ssm.domain.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Table;

@Getter
@Setter
@ToString
@Table(name = "t_yh_js")
public class TYhJs {
    @Column(name = "JSBH")
    private Integer jsbh;

    @Column(name = "YHBH")
    private Integer yhbh;
}