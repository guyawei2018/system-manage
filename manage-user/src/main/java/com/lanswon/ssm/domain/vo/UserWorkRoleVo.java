package com.lanswon.ssm.domain.vo;

import com.lanswon.ssm.domain.entity.TJs;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description: 用户工作角色信息
 * @Author GU-YW
 * @Date 2020/6/16 13:26
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserWorkRoleVo extends TJs {

   private String activeStatus;

   private String yhbh;

}
