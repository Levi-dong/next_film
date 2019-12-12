package com.next.demo.film.service.order;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.next.demo.film.common.utils.ToolUtils;
import com.next.demo.film.config.properties.OrderProperties;
import com.next.demo.film.controller.cinema.vo.CinemaFilmInfoVO;
import com.next.demo.film.controller.cinema.vo.FieldHallInfoVO;
import com.next.demo.film.controller.order.vo.response.OrderDetailResVO;
import com.next.demo.film.dao.entity.FilmOrderT;
import com.next.demo.film.dao.mapper.FilmOrderTMapper;
import com.next.demo.film.service.cinema.CinemaServiceAPI;
import com.next.demo.film.service.common.exception.CommonServiceException;
import com.next.demo.film.service.order.bo.OrderPriceBO;
import lombok.Cleanup;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderServiceAPI{

    @Autowired
    private FilmOrderTMapper filmOrderTMapper;
    @Autowired
    private CinemaServiceAPI cinemaServiceAPI;

    @Autowired
    private OrderProperties orderProperties;

    //private static final String FILE_PATH = orderProperties.ORDER_PREFIX;


    /*
    检查座位信息
    --文件信息会在分布式的文件存储|对象存储里
    FILE_PATH + seat_address= D:/ChromeCoreDownloads/seats/cgs.json
     */
    @Override
    public void checkSeats(String fieldId, String seats) throws CommonServiceException, IOException {
        FieldHallInfoVO soldSeats = cinemaServiceAPI.describeHallInfoByFieldId(fieldId);
        if (soldSeats==null || ToolUtils.isEmpty(soldSeats.getSeatFile())){
            throw new CommonServiceException(404,"场次编号不正确");
        }

        //String seatsPath = FILE_PATH + soldSeats.getSeatFile();
        String seatsPath = orderProperties.getFilePathPre() + soldSeats.getSeatFile();

        //关闭文件流
        @Cleanup
        BufferedReader bufferedReader = new BufferedReader(
                new FileReader(seatsPath)
        );

        StringBuffer stringBuffer = new StringBuffer();
        String temp = new String();
        while ((temp=bufferedReader.readLine())!=null) {
            stringBuffer.append(temp);
        }

        JSONObject jsonObject = JSON.parseObject(stringBuffer.toString());
        //获取ids节点
        String idsStr = jsonObject.getString("ids");

        /*
        用户购买：3，11，12
        ids:1-24
         */
        String[] ids = idsStr.split(",");
        List<String> idsList = Arrays.asList(ids);
        String[] seatArr = seats.split(",");

        for (String seatId:seatArr){
            boolean contains = idsList.contains(seatId);
            if (!contains){
                throw new CommonServiceException(500,"传入的座位信息有误");
            }
        }

    }

    /**
     * 检查待售卖得座位是否有已售座位信息
     */

    @Override
    public void checkSoldSeats(String fieldId, String seats) throws CommonServiceException {
        String soldSeats = filmOrderTMapper.describeSoldSeats(fieldId);
        /*
        用户购买：3，11，12
        ids:1-24
         */
        List<String> soldSeatsList = Arrays.asList(soldSeats.split(","));
        String[] seatArr = seats.split(",");

        for (String seatId:seatArr){
            boolean contains = soldSeatsList.contains(seatId);
            if (contains){
                throw new CommonServiceException(500,seatId+"为已售座位，不能重复销售");
            }
        }
    }

    /**
     * 根据用户编号，获取用户订单信息列表
     */

    @Override
    public IPage<OrderDetailResVO> describeOrderInfoByUserId(int nowPage,int pageSize,String userId) throws CommonServiceException {
        Page<FilmOrderT> page = new Page<>(nowPage,pageSize);
        return filmOrderTMapper.describeOrderDetailsByUserId(page, userId);
    }

    // seatIds = 1，2，3，4
    @Override
    public OrderDetailResVO saveOrder(String seatIds, String seatNames, String fieldId, String userId) throws CommonServiceException {

        // sdlfkj-sdjfksdf-sdfjksdf
        String uuid = UUID.randomUUID().toString().replace("-","");

        OrderPriceBO orderPriceBO = filmOrderTMapper.describeFilmPriceByFieldId(fieldId);
        // 单个座位的票价
        double filmPrice = orderPriceBO.getFilmPrice();
        // 销售的座位数 -> 票数
        int seatNum = seatIds.split(",").length;
        // 计算以后的总票价 - > 预留一个问题
        //double totalPrice = filmPrice * seatNum;
        double totalPrice = getTotalPrice(filmPrice , seatNum);
        // 获取filmId
        CinemaFilmInfoVO cinemaFilmInfoVO = cinemaServiceAPI.describeFilmInfoByFieldId(fieldId);

        FilmOrderT filmOrderT = new FilmOrderT();
        filmOrderT.setUuid(uuid);
        filmOrderT.setSeatsName(seatNames);
        filmOrderT.setSeatsIds(seatIds);
        filmOrderT.setOrderUser(Integer.parseInt(userId));
        filmOrderT.setOrderPrice(totalPrice);
        filmOrderT.setFilmPrice(filmPrice);
        filmOrderT.setFilmId(Integer.parseInt(cinemaFilmInfoVO.getFilmId()));
        filmOrderT.setFieldId(Integer.parseInt(fieldId));
        filmOrderT.setCinemaId(Integer.parseInt(orderPriceBO.getCinemaId()));

        //插入（插入成功返回数据，数据未必正确）
        filmOrderTMapper.insert(filmOrderT);

        //根据uuid在查出来，这样查出来是最终结果，并不是中间状态
        OrderDetailResVO orderDetailResVO = filmOrderTMapper.describeOrderDetailsById(uuid);

        return orderDetailResVO;
    }

    //计算总票价
    private double getTotalPrice(double filmPrice,int seatNum){
        BigDecimal b1 = new BigDecimal(filmPrice);
        BigDecimal b2 = new BigDecimal(seatNum);

        BigDecimal bigDecimal = b1.multiply(b2);

        //小数点后取两位，同时四舍五入
        BigDecimal result = bigDecimal.setScale(2, RoundingMode.HALF_UP);

        return result.doubleValue();
    }
}
