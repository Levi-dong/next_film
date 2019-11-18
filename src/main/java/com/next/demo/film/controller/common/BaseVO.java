package com.next.demo.film.controller.common;

import com.next.demo.film.controller.exception.ParamErrorException;

public abstract class BaseVO {

    public abstract void checkParam() throws ParamErrorException;
}
