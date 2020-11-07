package com.lanswon.ssm.domain.dto;

import com.lanswon.base.support.BasePageDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

/**
 * @Description: 组织查询参数
 * @Author GU-YW
 * @Date 2020/6/16 10:41
 */
@Data
@ApiModel(value = "单位查询参数")
@ToString(callSuper = true)
@Builder
public class CompanyDto extends BasePageDto {

    /**
     * 单位名
     */
    @ApiModelProperty(value = "单位名")
    private String companyName;

    /**
     * 单位编码
     */
    @ApiModelProperty(value = "单位编码")
    private String companyCode;
}
