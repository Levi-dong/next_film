package com.next.demo.film.dao.mapper;

import com.next.demo.film.controller.film.vo.response.filmdetail.ActorResultVO;
import com.next.demo.film.dao.entity.FilmActorT;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 演员表 Mapper 接口
 * </p>
 *
 * @author Levi
 * @since 2019-11-20
 */
public interface FilmActorTMapper extends BaseMapper<FilmActorT> {
    /*
    根据电影编号，获取对应的演员列表
     */
    List<ActorResultVO> describeActorsByFilmId(@Param("filmId") String filmId);



}
