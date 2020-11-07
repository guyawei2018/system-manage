package com.lanswon.ssm.domain.vo;

import lombok.Data;

import java.util.List;

/**
 * @Description:
 * @Author: GU-YW
 * @Date: 2020/7/3 17:44
 */
@Data
public class MenuTreeVo {

    private Integer qxbh;

    private String qxmc;

    private String qxlj;

    private Integer fjqxbh;

    private boolean hasPermission;

    private List<MenuTreeVo> children;
}
