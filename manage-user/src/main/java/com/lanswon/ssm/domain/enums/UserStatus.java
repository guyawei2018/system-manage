package com.lanswon.ssm.domain.enums;

/**
 * @Description: 用户状态枚举
 * @Author GU-YW
 * @Date 2020/06/16 17:00
 */
public enum UserStatus {

    /**
     * 未审核
     */
    OFF ("0","未审核"),
    /**
     * 已审核
     */
    ON ("1","已审核");


    public String code;

    public String desc;

     UserStatus(String code, String desc){
        this.code=code;
        this.desc=desc;
    }

    public static UserStatus getInstance(String code){
        UserStatus userStatus=null;
        for(UserStatus status:values()){
            if(status.code.equals(code)){
                userStatus=status;
                break;
            }
        }
        return userStatus;
    }
}
