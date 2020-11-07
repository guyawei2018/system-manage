package com.lanswon.ssm.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * @Description:
 * @Author: GU-YW
 * @Date: 2020/6/29 14:16
 */
@Data
@ApiModel(value = "角色权限绑定参数")
@ToString(callSuper = true)
@Builder
public class RolePermissionDto {

    @ApiModelProperty(value = "用户编号")
    private Integer roleId;

    @ApiModelProperty(value = "权限集合")
    private List<Integer> permissionId;

}
