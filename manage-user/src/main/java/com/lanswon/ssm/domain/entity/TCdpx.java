package com.lanswon.ssm.domain.entity;

import java.util.Date;
import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Table(name = "t_cdpx")
public class TCdpx {
    /**
     * 用户编号
     */
    @Id
    @Column(name = "YHBH")
    private Integer yhbh;

    /**
     * 前10个菜单排序
     */
    @Column(name = "CDPX")
    private String cdpx;

    /**
     * 更多菜单排序
     */
    @Column(name = "GDCDPX")
    private String gdcdpx;

    /**
     * 更新时间
     */
    @Column(name = "GXSJ")
    private Date gxsj;

    /**
     * 更新人
     */
    @Column(name = "GXR")
    private String gxr;

    /**
     * 创建时间
     */
    @Column(name = "CJSJ")
    private Date cjsj;

    /**
     * 创建人
     */
    @Column(name = "CJR")
    private String cjr;
}