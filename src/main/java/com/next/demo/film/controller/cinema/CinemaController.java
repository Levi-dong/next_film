package com.next.demo.film.controller.cinema;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.next.demo.film.config.properties.FilmProperties;
import com.next.demo.film.controller.cinema.vo.*;
import com.next.demo.film.controller.cinema.vo.condition.AreaResVO;
import com.next.demo.film.controller.cinema.vo.condition.BrandResVO;
import com.next.demo.film.controller.cinema.vo.condition.HallTypeResVO;
import com.next.demo.film.controller.cinema.vo.request.DescribeCinemaRequestVO;
import com.next.demo.film.controller.cinema.vo.response.FieldInfoRequestVO;
import com.next.demo.film.controller.common.BaseResponseVO;
import com.next.demo.film.service.cinema.CinemaServiceAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value="/cinema")
public class CinemaController {

    @Autowired
    private FilmProperties filmProperties;

    @Autowired
    private CinemaServiceAPI cinemaServiceAPI;

    @RequestMapping(value="/getFields",method = RequestMethod.GET)
    public BaseResponseVO getFields(
            String cinemaId
            //@RequestParam(name="cinemaId",required = true)String cinemaId
    ){
        //获取元数据
        List<CinemaFilmVO> cinemaFilms = cinemaServiceAPI.describeFieldsAndFilmInfo(cinemaId);
        CinemaDetailVO cinemaDetailVO = cinemaServiceAPI.describeCinemaDetails(cinemaId);

        //组织返回参数
        List<Map<String,CinemaFilmVO>> filmList = Lists.newArrayList();
        cinemaFilms.stream().forEach((film)->{
            Map<String,CinemaFilmVO> filmVOMap = Maps.newHashMap();
            filmVOMap.put("filmInfo",film);
            filmList.add(filmVOMap);
        });

        Map<String,Object> result = Maps.newHashMap();
        result.put("cinemaInfo",cinemaDetailVO);
        result.put("filmList",filmList);

        return BaseResponseVO.success(filmProperties.getImgPre(),result);
    }

    @RequestMapping(value="/getFieldInfo",method = RequestMethod.POST)
    public BaseResponseVO getFieldInfo(@RequestBody FieldInfoRequestVO requestVO){

        //获取逻辑层调用结果
        CinemaDetailVO cinemaDetailVO = cinemaServiceAPI.describeCinemaDetails(requestVO.getCinemaId());
        FieldHallInfoVO fieldHallInfoVO = cinemaServiceAPI.describeHallInfoByFieldId(requestVO.getFieldId());
        CinemaFilmInfoVO cinemaFilmInfoVO = cinemaServiceAPI.describeFilmInfoByFieldId(requestVO.getFieldId());

        //组织返回值
        Map<String,Object> result = Maps.newHashMap();
        result.put("filmInfo",cinemaFilmInfoVO);
        result.put("cinemaInfo",cinemaDetailVO);
        result.put("hallInfo",fieldHallInfoVO);

        return BaseResponseVO.success(filmProperties.getImgPre(),result);
    }

    @RequestMapping(value="/getCinemas",method = RequestMethod.GET)
    public BaseResponseVO getCinemas(DescribeCinemaRequestVO requestVO){

        //获取逻辑层调用结果
        Page<CinemaVO> cinemaVOPage = cinemaServiceAPI.describeCinemaInfo(requestVO);
        //组织返回值
        Map<String,Object> result = Maps.newHashMap();
        result.put("cinemas",cinemaVOPage.getRecords());

        return BaseResponseVO.success(cinemaVOPage.getCurrent(),cinemaVOPage.getPages(),filmProperties.getImgPre(),result);
    }

    @RequestMapping(value="/getCondition",method = RequestMethod.GET)
    public BaseResponseVO getCondition(
            @RequestParam(name="brandId",required = false,defaultValue = "99")Integer brandId,
            @RequestParam(name="hallType",required = false,defaultValue = "99")Integer hallType,
            @RequestParam(name="areaId",required = false,defaultValue = "99")Integer areaId
    ){
        //验证id是否有效
        if(!cinemaServiceAPI.checkCondition(brandId,"brand")) brandId = 99;
        if(!cinemaServiceAPI.checkCondition(hallType,"hallType")) hallType = 99;
        if(!cinemaServiceAPI.checkCondition(areaId,"area")) areaId = 99;

        //获取逻辑层调用结果
        List<BrandResVO> brandResVOS = cinemaServiceAPI.describeBrandConditions(brandId);
        List<HallTypeResVO> hallTypeResVOS = cinemaServiceAPI.describeHallTypeConditions(hallType);
        List<AreaResVO> areaResVOS = cinemaServiceAPI.describeAreaConditions(areaId);

        //组织返回值
        Map<String,Object> result = Maps.newHashMap();
        result.put("areaList",areaResVOS);
        result.put("halltypeList",hallTypeResVOS);
        result.put("brandList",brandResVOS);

        return BaseResponseVO.success(result);
    }

}
