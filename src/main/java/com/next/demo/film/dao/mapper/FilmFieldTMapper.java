package com.next.demo.film.dao.mapper;

import com.next.demo.film.controller.cinema.vo.CinemaFilmInfoVO;
import com.next.demo.film.controller.cinema.vo.CinemaFilmVO;
import com.next.demo.film.controller.cinema.vo.FieldHallInfoVO;
import com.next.demo.film.dao.entity.FilmFieldT;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 放映场次表 Mapper 接口
 * </p>
 *
 * @author Levi
 * @since 2019-11-17
 */
public interface FilmFieldTMapper extends BaseMapper<FilmFieldT> {

    List<CinemaFilmVO> describeFieldList(@Param("cinemaId")String cinemaId);

    CinemaFilmInfoVO describeFilmInfoByFieldId(@Param("fieldId")String fieldId);

    FieldHallInfoVO describeHallInfo(@Param("fieldId")String fieldId);
}
