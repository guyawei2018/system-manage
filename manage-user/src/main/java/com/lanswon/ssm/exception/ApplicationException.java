package com.lanswon.ssm.exception;

import lombok.Data;

/** 自定义异常
 * @Description:
 * @Author GU-YW
 * @Date 2019/12/3 16:52
 */
@Data
public class ApplicationException extends Exception{

    private int errorCode;

    public ApplicationException(int code, String msg) {
        super(msg);
        this.errorCode = code;

    }

    @Override
    public String toString() {
        return "AppException [errorCode=" + errorCode + "]:\t"
                + this.getMessage();
    }
}
