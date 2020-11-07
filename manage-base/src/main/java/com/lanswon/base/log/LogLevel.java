package com.lanswon.base.log;

/**
 * @Description: 日志级别
 * @Author GU-YW
 * @Date 2020/06/16 17:00
 */
public enum LogLevel {


    OK (1,"正常"),

    ERROR (2,"异常");


    public int code;

    public String desc;

     LogLevel(int code, String desc){
        this.code=code;
        this.desc=desc;
    }

    public static LogLevel getInstance(int code){
        LogLevel enable=null;
        for(LogLevel status:values()){
            if(status.code == code){
                enable=status;
                break;
            }
        }
        return enable;
    }
}
