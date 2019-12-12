package com.next.demo.film.controller.cinema.vo.response;

import com.next.demo.film.controller.common.BaseVO;
import com.next.demo.film.controller.exception.ParamErrorException;
import lombok.Data;

import java.io.Serializable;

@Data
public class FieldInfoRequestVO extends BaseVO implements Serializable {

    private String cinemaId;
    private String fieldId;

    @Override
    public void checkParam() throws ParamErrorException {

    }
}
