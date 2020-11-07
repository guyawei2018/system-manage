package com.lanswon.ssm.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description:
 * @Author: GU-YW
 * @Date: 2020/7/7 16:28
 */
@Data
public class OauthUrl implements Serializable {

    private String qxff;

    private String qxlj;

    private Integer qxbh;

    private String qxlx;

    private String fjqxbh;
}
