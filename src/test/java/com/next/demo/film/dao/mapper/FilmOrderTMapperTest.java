package com.next.demo.film.dao.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.next.demo.film.controller.order.vo.response.OrderDetailResVO;
import com.next.demo.film.dao.entity.FilmOrderT;
import com.next.demo.film.service.order.bo.OrderPriceBO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
public class FilmOrderTMapperTest {

    @Autowired
    private FilmOrderTMapper orderTMapper;

    @Test
    public void describeOrderDetailsById() {
        OrderDetailResVO orderDetailResVO = orderTMapper.describeOrderDetailsById("415sdf58ew12ds5fe1");
        System.out.println(orderDetailResVO);
    }

    @Test
    public void describeOrderDetailsByUserId() {
        Page<FilmOrderT> page = new Page<>(1,10);
        IPage<OrderDetailResVO> orderDetailResVOIPage = orderTMapper.describeOrderDetailsByUserId(page, "1");
        orderDetailResVOIPage.getRecords().stream().forEach(System.out::println);
    }

    @Test
    public void describeFilmPriceByFieldId() {
        OrderPriceBO orderPriceBO = orderTMapper.describeFilmPriceByFieldId("1");
        System.out.println(orderPriceBO);
    }

    @Test
    public void describeSoldSeats() {
        String seats = orderTMapper.describeSoldSeats("1");
        System.out.println(seats);
    }
}
