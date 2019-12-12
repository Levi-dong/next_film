package com.next.demo.film.service.order;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.next.demo.film.controller.order.vo.response.OrderDetailResVO;
import com.next.demo.film.service.common.exception.CommonServiceException;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface OrderServiceAPI {

    /*
    检查座位是否符合现状
     */
    void checkSeats(String fieldId,String seats) throws CommonServiceException, IOException;

    /*
    检查座位是否已售座位
     */
    void checkSoldSeats(String fieldId,String seats) throws CommonServiceException;

    /*
    保存订单信息
     */
    OrderDetailResVO saveOrder(String seatIds,String seatNames,String fieldId,String userId) throws CommonServiceException;

    /*
    根据用户编号，获取该用户购买过的电影票订单信息
     */
    IPage<OrderDetailResVO> describeOrderInfoByUserId(int nowPage,int pageSize,String userId) throws CommonServiceException;
}
