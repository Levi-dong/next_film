package com.next.demo.film.controller.order.vo.response;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author Clearlove
 * @Date 2019/12/16 0:01
 * @Version 1.0
 */

@Data
public class QRCodeResVO implements Serializable {
    private String orderId;
    private String QRCodeAddress;
}
