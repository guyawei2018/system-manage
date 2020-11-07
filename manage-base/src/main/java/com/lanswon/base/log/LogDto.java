package com.lanswon.base.log;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Description:
 * @Author: GU-YW
 * @Date: 2020/7/13 13:24
 */
@Data
@ApiModel(value = "日志对象")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LogDto implements Serializable {

    //==============公共

    @ApiModelProperty(value = "操作人姓名",required = true)
    private String userName;

    @ApiModelProperty(value = "操作人身份证号码",required = true)
    private String cardNo;

    @ApiModelProperty(value = "操作人警号",required = true)
    private String policeNo;

    @ApiModelProperty(value = "操作人所属单位代码",required = true)
    private String orgCode;

    @ApiModelProperty(value = "操作人所属单位名称",required = true)
    private String orgName;

    @ApiModelProperty(value = "日志类型：1-查询 2-新增 3-修改 4-删除 0-登录 -1退出",required = true)
    private Integer logType;

    @ApiModelProperty(value = "操作终端  公安内网pc填IP 互联网移动终端填手机号",required = true)
    private String terminalNo;

    @ApiModelProperty(value = "操作时间 yyyy-MM-dd HH:mm:ss",required = true)
    private String operateTime;

    @ApiModelProperty(value = "日志级别  1-正常  2-异常" ,required = true)
    private Integer logLevel;

    @ApiModelProperty(value = "登录成功后的操作行为必填：操作日志详情")
    private String logDetail;

    @ApiModelProperty(value = "登录成功后的操作内容所属的业务模块详情")
    private String moduleName;

    //================登录

    @ApiModelProperty(value = "登录动作必填，登录方式 1-手机验证码+人脸  2-证书+人脸 3-移动端账号密码+人脸")
    private Integer loginWay;

    @ApiModelProperty(value = "登录动作必填，操作人脸base64")
    private String userFace;


}
