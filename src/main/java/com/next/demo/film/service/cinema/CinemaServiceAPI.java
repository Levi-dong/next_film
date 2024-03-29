package com.next.demo.film.service.cinema;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.next.demo.film.controller.cinema.vo.*;
import com.next.demo.film.controller.cinema.vo.condition.AreaResVO;
import com.next.demo.film.controller.cinema.vo.condition.BrandResVO;
import com.next.demo.film.controller.cinema.vo.condition.HallTypeResVO;
import com.next.demo.film.controller.cinema.vo.request.DescribeCinemaRequestVO;

import java.util.List;

public interface CinemaServiceAPI {

    /*
    根据查询条件影院列表
     */
    Page<CinemaVO> describeCinemaInfo(DescribeCinemaRequestVO describeCinemaRequestVO);

    /*
    获取查询条件
     */
    boolean checkCondition(int conditionId,String conditionType);
    List<BrandResVO> describeBrandConditions(int brandId);
    List<HallTypeResVO> describeHallTypeConditions(int hallTypeId);
    List<AreaResVO> describeAreaConditions(int areaId);

    /*
     根据影院编号，获取影院信息
     */
    CinemaDetailVO describeCinemaDetails(String cinemaId);

    /*
    根据影院编号，获取场次信息
     */
    List<CinemaFilmVO> describeFieldsAndFilmInfo(String cinemaId);

    /*
    根据场次编号，获取电影信息
     */
    CinemaFilmInfoVO describeFilmInfoByFieldId(String fieldId);

    /*
    根据场次编号，获取放映厅信息
     */
    FieldHallInfoVO describeHallInfoByFieldId(String fieldId);
}
