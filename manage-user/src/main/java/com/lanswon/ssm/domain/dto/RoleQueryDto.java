package com.lanswon.ssm.domain.dto;

import com.lanswon.base.support.BasePageDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

/**
 * @Description: 系统用户查询参数
 * @Author GU-YW
 * @Date 2020/6/16 10:41
 */
@Data
@ApiModel(value = "系统角色查询参数")
@ToString(callSuper = true)
@Builder
public class RoleQueryDto extends BasePageDto {

    /**
     * 角色名
     */
    @ApiModelProperty(value = "角色名")
    private String roleName;
}
