package com.lanswon.ssm.domain.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
@ToString
@Table(name = "t_js")
public class TJs {
    /**
     * 角色编号
     */
    @Id
    @Column(name = "JSBH")
    private Integer jsbh;

    /**
     * 角色名称
     */
    @Column(name = "JSMC")
    @NotNull(message = "角色名称不能为空")
    private String jsmc;

    /**
     * 角色代码
     */
    @Column(name = "JSDM")
    @NotNull(message = "角色代码不能为空")
    private String jsdm;

    /**
     * 角色描述信息
     */
    @Column(name = "JSMS")
    private String jsms;

    /**
     * 启用状态   0-停用 1-启用
     */
    @Column(name = "JSZT")
    private String jszt;

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

    /**
     * 创建ip
     */
    @Column(name = "CJDZ")
    private String cjdz;

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
     * 更新IP
     */
    @Column(name = "GXDZ")
    private String gxdz;

    /**
     * 角色类型 1-工作角色 2-系统角色
     */
    @Column(name = "JSLX")
    private String jslx;

    /**
     * 工作方式 1-处理(情报指挥) 2-关注(领导、总指挥长、指挥长、情报研判、综合管理、各种联勤、总值班长、值班长）)3-警种联动 和 接警巡逻  4-其他人员（本部门全部警情 无需操作）
     */
    @Column(name = "GZFS")
    private String gzfs;

    /**
     * 层级类型  1-市 2-区  3-基层
     */
    @Column(name = "CJLX")
    private String cjlx;
}