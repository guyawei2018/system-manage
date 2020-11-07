package com.lanswon.base.support;

import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @Description: 统一返回对象
 * @Author GU-YW
 * @Date 2020/6/16 09:49
 */
@Slf4j
@Data
@Builder
public class SimpleResponse <T>{

    /**
     * 返回状态码
     */
    private int code;

    /**
     * 返回描述信息
     */
    private String msg;

    /**
     * 返回数据
     */
    private T data;

    public static SimpleResponse ok(String msg){
        return SimpleResponse.builder().code(200).msg(msg).build();
    }

    public static SimpleResponse ok(Object data){
        return SimpleResponse.builder().code(200).data(data).build();
    }
}
