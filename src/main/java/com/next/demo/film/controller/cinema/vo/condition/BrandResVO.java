package com.next.demo.film.controller.cinema.vo.condition;

import lombok.Data;

import java.io.Serializable;

@Data
public class BrandResVO implements Serializable {

    private String brandId;
    private String brandName;
    private String isActive;

}
