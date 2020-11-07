package com.lanswon.ssm.vo;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * @Description:
 * @Author: GU-YW
 * @Date: 2020/7/7 16:26
 */
@Data
@ToString(callSuper = true)
public class OauthUser implements Serializable {

    private String sfzhm;

    private List<OauthUrl> urls;

}
