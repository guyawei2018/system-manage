package com.lanswon.ssm.domain.enums;

/**
 * @Description: 账号状态枚举
 * @Author GU-YW
 * @Date 2020/06/16 17:00
 */
public enum RoleStatus {

    /**
     * 停用
     */
    OFF ("0","停用"),
    /**
     * 启用
     */
    ON ("1","启用");


    public String code;

    public String desc;

     RoleStatus(String code, String desc){
        this.code=code;
        this.desc=desc;
    }

    public static RoleStatus getInstance(String code){
        RoleStatus enable=null;
        for(RoleStatus status:values()){
            if(status.code.equals(code)){
                enable=status;
                break;
            }
        }
        return enable;
    }
}
