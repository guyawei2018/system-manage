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
 * @Date: 2020/6/22 16:41
 */
@Data
@ApiModel(value = "菜单排序参数")
@ToString(callSuper = true)
@Builder
public class MenuDto {

    @ApiModelProperty(value = "用户编号")
    private Integer yhbh;

    @ApiModelProperty(value = "前十个菜单顺序")
    private List<String> menuIds;

    @ApiModelProperty(value = "更多菜单顺序")
    private List<String> moreIds;

}
