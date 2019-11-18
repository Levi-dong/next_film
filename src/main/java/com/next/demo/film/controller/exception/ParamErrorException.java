package com.next.demo.film.controller.exception;

import lombok.Data;

/*
VO 参数验证异常
 */
@Data
public class ParamErrorException extends Exception{
    private Integer code;
    private String errMsg;

    public ParamErrorException(int code, String errMsg){
        super(errMsg);
        this.code = code;
        this.errMsg = errMsg;
    }
}
