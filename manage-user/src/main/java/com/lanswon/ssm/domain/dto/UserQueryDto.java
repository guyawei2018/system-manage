package com.lanswon.ssm.domain.dto;

import com.lanswon.base.support.BasePageDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

/**
 * @Description:
 * @Author: GU-YW
 * @Date: 2020/7/7 15:57
 */
@Data
@ApiModel(value = "系统用户分页查询参数")
@ToString(callSuper = true)
@Builder
public class UserQueryDto extends BasePageDto {

    /**
     * 用户状态
     */
    @ApiModelProperty(value = "用户状态")
    private String status;

    /**
     * 用户状态
     */
    @ApiModelProperty(value = "用户状态")
    private String param;
}
