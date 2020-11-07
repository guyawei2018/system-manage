package com.lanswon.ssm.domain.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Table;

@Getter
@Setter
@ToString
@Table(name = "t_js_qx")
public class TJsQx {
    @Column(name = "QXBH")
    private Integer qxbh;

    @Column(name = "JSBH")
    private Integer jsbh;
}