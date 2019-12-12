package com.next.demo.film.service.film;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.next.demo.film.controller.film.FilmEnum;
import com.next.demo.film.controller.film.vo.request.DescribeFilmListReqVO;
import com.next.demo.film.controller.film.vo.response.condition.CatInfoResultVO;
import com.next.demo.film.controller.film.vo.response.condition.SourceInfoResultVO;
import com.next.demo.film.controller.film.vo.response.condition.YearInfoResultVO;
import com.next.demo.film.controller.film.vo.response.filmdetail.ActorResultVO;
import com.next.demo.film.controller.film.vo.response.filmdetail.FilmDetailResultVO;
import com.next.demo.film.controller.film.vo.response.filmdetail.ImagesResultVO;
import com.next.demo.film.controller.film.vo.response.films.DescribeFilmListResultVO;
import com.next.demo.film.controller.film.vo.response.index.BannerInfoResultVO;
import com.next.demo.film.controller.film.vo.response.index.HotFilmListResultVO;
import com.next.demo.film.controller.film.vo.response.index.RankFilmListResultVO;
import com.next.demo.film.controller.film.vo.response.index.SoonFilmListResultVO;
import com.next.demo.film.dao.entity.FilmInfoT;
import com.next.demo.film.service.common.exception.CommonServiceException;

import java.util.List;

public interface FilmServiceAPI {
    /*
        Banner信息获取
     */
    List<BannerInfoResultVO> describeBanners() throws CommonServiceException;

    // 拆分 - 合并
    /*
        获取热映影片
     */
    List<HotFilmListResultVO> describeHotFilms() throws CommonServiceException;
    /*
        获取即将上映影片
     */
    List<SoonFilmListResultVO> describeSoonFilms() throws CommonServiceException;

    /*
        获取热映或即将上映的影片数量
        filmType 1-热映 2-即将上映
     */
    int describeIndexFilmNum(String filmType) throws CommonServiceException;

    /*
        票房排行
     */
    List<RankFilmListResultVO> boxRandFilms() throws CommonServiceException;

    /*
        期待排行
     */
    List<RankFilmListResultVO> expectRandFilms() throws CommonServiceException;

    /*
        Top100 排行
     */
    List<RankFilmListResultVO> topRandFilms() throws CommonServiceException;

    /*
        验证有效性
     */
    String checkCondition(String conditionId, FilmEnum filmEnum) throws CommonServiceException;

    /*
        三种条件查询
     */
    List<CatInfoResultVO> describeCatInfos(String catId) throws CommonServiceException;
    List<SourceInfoResultVO> describeSourceInfos(String sourceId) throws CommonServiceException;
    List<YearInfoResultVO> describeYearInfos(String yearId) throws CommonServiceException;

    /*
        根据条件，获取对应的电影列表信息
     */
    IPage<FilmInfoT> describeFilms(DescribeFilmListReqVO describeFilmListReqVO) throws CommonServiceException;

    /*
        获取电影详情
        searchType -> 0-按编号查询，1-按名称模糊匹配
     */
     FilmDetailResultVO describeFilmDetails(String searchStr,String searchType) throws CommonServiceException;

     /*
        获取影片描述信息
     */
     String describeFilmBiography(String filmId) throws CommonServiceException;

     /*
        获取影片图片信息
     */
     ImagesResultVO describeFilmImages(String filmId) throws CommonServiceException;

     /*
        获取导演信息
     */
     ActorResultVO describeDirector(String filmId) throws CommonServiceException;

     /*
        获取演员信息
     */
     List<ActorResultVO> describeActors(String filmId) throws CommonServiceException;
}
