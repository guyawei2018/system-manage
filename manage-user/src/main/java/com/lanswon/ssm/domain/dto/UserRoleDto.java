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
@ApiModel(value = "用户与工作角色处理参数")
@ToString(callSuper = true)
@Builder
public class UserRoleDto {

    @ApiModelProperty(value = "用户编号")
    private Integer yhbh;

    @ApiModelProperty(value = "角色编号集合")
    private List<Integer> ids;

}
