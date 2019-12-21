package com.next.demo.film.controller.order.vo.response;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author Clearlove
 * @Date 2019/12/16 0:40
 * @Version 1.0
 */

@Data
public class OrderPayResVO implements Serializable {

    private String orderId;
    private Integer orderStatus;
    private String orderMsg = "订单支付消息";
}
