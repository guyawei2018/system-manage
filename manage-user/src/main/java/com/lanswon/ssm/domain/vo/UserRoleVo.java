package com.lanswon.ssm.domain.vo;

import com.lanswon.ssm.domain.entity.TJs;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

/**
 * @Description: 用户工作角色信息
 * @Author GU-YW
 * @Date 2020/6/16 13:26
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRoleVo {

   /**
    * 角色编号
    */
   private Integer jsbh;

   /**
    * 角色名称
    */
   private String jsmc;

   /**
    * 角色代码
    */
   private String jsdm;

}
