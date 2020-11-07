package com.lanswon.ssm.domain.vo;

import com.lanswon.ssm.domain.entity.TYh;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description:
 * @Author GU-YW
 * @Date 2020/6/16 13:26
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserVo extends TYh {

    /**
     * 工作单位
     */
    private String orgName;

    /**
     * 用户状态描述
     */
    private String userStatusDec;

    /**
     * 账号状态描述
     */
    private String enabledDec;

}
