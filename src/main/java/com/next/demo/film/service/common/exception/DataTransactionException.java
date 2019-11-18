package com.next.demo.film.service.common.exception;

import lombok.Data;

@Data
public class DataTransactionException extends Exception {

    private Integer code;
    private String errMsg;

    public DataTransactionException(int code,String errMsg){
        super(errMsg);
        this.code = code;
        this.errMsg = errMsg;
    }
}
