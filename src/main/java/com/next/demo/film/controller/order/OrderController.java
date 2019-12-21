package com.next.demo.film.controller.order;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.google.common.collect.Maps;
import com.google.common.util.concurrent.RateLimiter;
import com.next.demo.film.controller.common.BaseResponseVO;
import com.next.demo.film.controller.common.TraceUtil;
import com.next.demo.film.controller.exception.ParamErrorException;
import com.next.demo.film.controller.order.vo.response.OrderDetailResVO;
import com.next.demo.film.controller.order.vo.response.OrderPayResVO;
import com.next.demo.film.controller.order.vo.response.QRCodeResVO;
import com.next.demo.film.example.alipay.trade.config.Configs;
import com.next.demo.film.service.common.exception.CommonServiceException;
import com.next.demo.film.service.order.OrderServiceAPI;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderServiceAPI orderServiceAPI;

    static {
        Configs.init("zfbinfo.properties");
    }

    @RequestMapping(value="/buyTickets",method = RequestMethod.GET)
    public BaseResponseVO buyTickets(String fieldId,String soldSeats,String seatsName) throws CommonServiceException {

        //购票限流措施
        RateLimiter rateLimiter = RateLimiter.create(20);
        rateLimiter.acquire(1);

        // soldSeats 验证是否为真实有效的座位信息
        try {
            orderServiceAPI.checkSeats(fieldId,soldSeats);
        } catch (IOException e) {
            throw new CommonServiceException(404,"场次座位信息无法读取！");
        }
        // soldSeats 验证是否是未销售的座位
        orderServiceAPI.checkSoldSeats(fieldId,soldSeats);

        String userId = TraceUtil.getUserId();

        OrderDetailResVO orderDetailResVO = orderServiceAPI.saveOrder(soldSeats, seatsName, fieldId, userId);

        return BaseResponseVO.success(orderDetailResVO);
    }


    @RequestMapping(value="/getOrderInfo",method = RequestMethod.GET)
    public BaseResponseVO getOrderInfo(
            @RequestParam(name = "nowPage",required = false,defaultValue = "1") int nowPage,
            @RequestParam(name = "pageSize",required = false,defaultValue = "5") int pageSize) throws ParamErrorException, CommonServiceException {
        checkGetOrderInfoParams(nowPage,pageSize);
        // 使用用户的JWT信息来获取内容
        String userId = TraceUtil.getUserId();

        IPage<OrderDetailResVO> orderDetailResVOIPage = orderServiceAPI.describeOrderInfoByUserId(nowPage, pageSize, userId);

        return BaseResponseVO.success(orderDetailResVOIPage.getCurrent(),orderDetailResVOIPage.getPages(),orderDetailResVOIPage.getRecords());
    }

    private void checkGetOrderInfoParams(int nowPage,int pageSize) throws ParamErrorException {
        // nowpage必须大于1
        // pageSize必须大于0
    }

    //@RequestMapping(value="/getPayInfo",method = RequestMethod.GET)
    @GetMapping(value = "/getPayInfo")
    public BaseResponseVO getPayInfo(String orderId) throws CommonServiceException {
        QRCodeResVO qrCodeResVO = orderServiceAPI.describeQRCodeAddress(orderId);
        return BaseResponseVO.success(qrCodeResVO);
    }

    @GetMapping(value = "/getPayResult")
    public BaseResponseVO getPayResult(String orderId,@RequestParam(name="tryNums",required = false,defaultValue = "1") Integer tryNums) throws CommonServiceException {
        if (tryNums<4 ){
            OrderPayResVO orderPayResVO = orderServiceAPI.describePayResult(orderId);
            return BaseResponseVO.success(orderPayResVO);
        }else {
            throw new CommonServiceException(500,"支付超时");
        }

    }

    //@RequestMapping(value = "/alipay/callback",method = RequestMethod.POST)
    @PostMapping("/alipay/callback")
    public void alipayCallback(HttpServletRequest request, HttpServletResponse response) throws AlipayApiException {
        //1、获取request里所有的与alipay相关的参数，封装成一个map
        Map<String,String> parameterMap = Maps.newHashMap();
        Enumeration<String> parameterNames = request.getParameterNames();
        // 同样效果
        // Map<String, String[]> parameterMap1 = request.getParameterMap();
        while (parameterNames.hasMoreElements()){
            String parameterName = parameterNames.nextElement();
            System.out.println(parameterName+","+request.getParameter(parameterName));
            parameterMap.put(parameterName,request.getParameter(parameterName));
        }

        //2、验证请求是否是alipay返回的请求内容【验证请求合法性】
        //重要
        parameterMap.remove("sign_type");
        boolean isSuccess = AlipaySignature.rsaCheckV2(parameterMap, Configs.getAlipayPublicKey(), "utf-8", Configs.getSignType());
        log.info("alipay callback result:{}",isSuccess);
        PrintWriter out = null;
        try {
            out = response.getWriter();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //3、如果返回成功，返回一个success
        if (isSuccess){
            try {
                orderServiceAPI.orderPaySuccess(parameterMap.get("out_trade_no"));
            } catch (CommonServiceException e) {
                log.error("支付失败，原因是:{}",e);
                out.print("unSuccess");
            }
            out.print("success");
        }else{
            try {
                orderServiceAPI.orderPaySuccess(parameterMap.get("out_trade_no"));
            } catch (CommonServiceException e) {
                log.error("订单关闭失败，原因是:{}",e);
                out.print("unSuccess");
            }
            out.print("unSuccess");
        }
    }
}
