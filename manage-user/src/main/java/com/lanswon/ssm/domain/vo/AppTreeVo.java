package com.lanswon.ssm.domain.vo;

import lombok.Data;

import java.util.List;

/**
 * @Description:
 * @Author: GU-YW
 * @Date: 2020/7/3 17:44
 */
@Data
public class AppTreeVo {

    /**
     * 组织名称
     */
    private String zzmc;

    /**
     * 组织编码
     */
    private String zzbm;

    /**
     * 父级组织编号
     */
    private String fjzzbm;

    private List<AppTreeVo> children;
}
