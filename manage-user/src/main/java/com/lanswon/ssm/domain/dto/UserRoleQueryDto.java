package com.lanswon.ssm.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

/**
 * @Description:
 * @Author: GU-YW
 * @Date: 2020/6/29 14:16
 */
@Data
@ApiModel(value = "用户角色查询参数")
@ToString(callSuper = true)
@Builder
public class UserRoleQueryDto {

    @ApiModelProperty(value = "身份证号码")
    private String idCard;

    @ApiModelProperty(value = "手机号码")
    private String phone;

}
