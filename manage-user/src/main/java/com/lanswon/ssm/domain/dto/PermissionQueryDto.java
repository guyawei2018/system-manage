package com.lanswon.ssm.domain.dto;

import com.lanswon.base.support.BasePageDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

/**
 * @Description: 系统权限查询参数
 * @Author GU-YW
 * @Date 2020/6/16 10:41
 */
@Data
@ApiModel(value = "系统权限查询参数")
@ToString(callSuper = true)
@Builder
public class PermissionQueryDto extends BasePageDto {

    /**
     * 权限名
     */
    @ApiModelProperty(value = "权限名")
    private String permissionName;

    @ApiModelProperty(value = "权限类型")
    private String [] permissionTypes;
}
