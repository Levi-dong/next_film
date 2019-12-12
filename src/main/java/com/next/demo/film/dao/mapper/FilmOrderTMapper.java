package com.next.demo.film.dao.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.next.demo.film.controller.order.vo.response.OrderDetailResVO;
import com.next.demo.film.dao.entity.FilmOrderT;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.next.demo.film.service.order.bo.OrderPriceBO;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 订单信息表 Mapper 接口
 * </p>
 *
 * @author Levi
 * @since 2019-11-24
 */
public interface FilmOrderTMapper extends BaseMapper<FilmOrderT> {

    OrderDetailResVO describeOrderDetailsById(@Param("orderId")String orderId);

    IPage<OrderDetailResVO> describeOrderDetailsByUserId(Page<FilmOrderT> page, @Param("userId")String userId);

    OrderPriceBO describeFilmPriceByFieldId(@Param("fieldId") String fieldId);

    String describeSoldSeats(@Param("fieldId") String fieldId);
}
