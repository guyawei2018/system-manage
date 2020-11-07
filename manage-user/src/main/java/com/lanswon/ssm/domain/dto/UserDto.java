package com.lanswon.ssm.domain.dto;

import com.lanswon.ssm.domain.entity.TYh;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

/**
 * @Description:
 * @Author: GU-YW
 * @Date: 2020/6/22 16:41
 */
@Data
@ApiModel(value = "添加人脸参数")
@ToString(callSuper = true)
@Builder
public class UserDto extends TYh {

    @ApiModelProperty(value = "图片码")
    private String imageBase64;

    @ApiModelProperty(value = "手机验证码")
    private String smsCode;

    @ApiModelProperty(value = "是否系统内部注册 0- 用户自定义注册 1-系统内新增")
    private Integer isSystemIn;

}
