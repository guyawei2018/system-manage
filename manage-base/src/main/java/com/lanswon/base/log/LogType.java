package com.lanswon.base.log;

/**
 * @Description: 日志类型
 * @Author GU-YW
 * @Date 2020/06/16 17:00
 */
public enum LogType {

    SELECT (1,"查询"),

    INSERT (2,"新增"),

    PUT (3,"修改"),

    DELETE (4,"删除"),

    LOGIN (0,"登录"),

    LOGOUT (-1,"退出");


    public int code;

    public String desc;

     LogType(int code, String desc){
        this.code=code;
        this.desc=desc;
    }

    public static LogType getInstance(int code){
        LogType enable=null;
        for(LogType status:values()){
            if(status.code == code){
                enable=status;
                break;
            }
        }
        return enable;
    }
}
