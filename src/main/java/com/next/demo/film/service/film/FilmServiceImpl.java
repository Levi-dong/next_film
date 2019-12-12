package com.next.demo.film.service.film;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.next.demo.film.common.utils.ToolUtils;
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
import com.next.demo.film.dao.entity.*;
import com.next.demo.film.dao.mapper.*;
import com.next.demo.film.service.common.exception.CommonServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class FilmServiceImpl implements FilmServiceAPI{
    @Autowired
    private FilmSourceDictTMapper sourceDictTMapper;
    @Autowired
    private FilmYearDictTMapper yearDictTMapper;
    @Autowired
    private FilmCatDictTMapper catDictTMapper;

    @Autowired
    private FilmBannerTMapper bannerTMapper;

    @Autowired
    private FilmInfoTMapper filmInfoTMapper;
    @Autowired
    private FilmDetailTMapper filmDetailTMapper;

    @Autowired
    private FilmActorTMapper actorTMapper;
    @Autowired
    private FilmActorRelaTMapper actorRelaTMapper;


    @Override
    public List<BannerInfoResultVO> describeBanners() throws CommonServiceException {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("is_valid","0");

        List<FilmBannerT> banners = bannerTMapper.selectList(queryWrapper);
        List<BannerInfoResultVO> result = Lists.newArrayList();

        // 组织返回的结果
        banners.parallelStream().forEach((banner)->{
                BannerInfoResultVO bannerInfoResultVO = new BannerInfoResultVO();
                bannerInfoResultVO.setBannerId(banner.getUuid().toString());
                bannerInfoResultVO.setBannerUrl(banner.getBannerUrl());
                bannerInfoResultVO.setBannerAddress(banner.getBannerAddress());

                result.add(bannerInfoResultVO);
        });

        return result;
    }

    @Override
    public List<HotFilmListResultVO> describeHotFilms() throws CommonServiceException {
        // 默认热映的影片在首页中只查看8条记录
        Page<FilmInfoT> page = new Page<>(1,8);
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("film_status","1");

        IPage<FilmInfoT> iPage = filmInfoTMapper.selectPage(page, queryWrapper);
        List<HotFilmListResultVO> results = Lists.newArrayList();

        iPage.getRecords().stream().forEach((film)->{
            HotFilmListResultVO result = new HotFilmListResultVO();
            result.setFilmId(film.getUuid().toString());
            result.setImgAddress(film.getImgAddress());
            result.setFilmType(film.getFilmType().toString());
            result.setFilmScore(film.getFilmSource().toString());
            result.setFilmName(film.getFilmName());

            results.add(result);
        });


        return results;
    }

    @Override
    public List<SoonFilmListResultVO> describeSoonFilms() throws CommonServiceException {
        // 默认即将上映的影片在首页中只查看8条记录
        Page<FilmInfoT> page = new Page<>(1,8);
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("film_status","2");

        IPage<FilmInfoT> iPage = filmInfoTMapper.selectPage(page, queryWrapper);
        List<SoonFilmListResultVO> results = Lists.newArrayList();

        iPage.getRecords().stream().forEach((film)->{
            SoonFilmListResultVO vo = new SoonFilmListResultVO();
            vo.setFilmType(film.getFilmType().toString());
            vo.setImgAddress(film.getImgAddress());
            vo.setFilmName(film.getFilmName());
            vo.setFilmId(film.getUuid().toString());
            vo.setExpectNum(film.getFilmPresalenum().toString());
            vo.setShowTime(localTime2Str(film.getFilmTime()));
            results.add(vo);
        });
        return results;
    }

    @Override
    public int describeIndexFilmNum(String filmType) throws CommonServiceException {
        QueryWrapper queryWrapper = new QueryWrapper();
        if("2".equals(filmType)){
            queryWrapper.eq("film_status","2");
        }else {
            queryWrapper.eq("film_status","1");
        }
        return filmInfoTMapper.selectCount(queryWrapper);
    }

    private String localTime2Str(LocalDateTime localDateTime){
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return dateTimeFormatter.format(localDateTime);
    }

    /*
        票房排行 - 正在热映的电影，top10
     */
    @Override
    public List<RankFilmListResultVO> boxRandFilms() throws CommonServiceException {
        Page<FilmInfoT> page = new Page<>(1,10);
        page.setDesc("film_box_office");
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("film_status","1");

        IPage<FilmInfoT> iPage = filmInfoTMapper.selectPage(page, queryWrapper);

        List<RankFilmListResultVO> results = Lists.newArrayList();

        iPage.getRecords().stream().forEach((film)->{
            RankFilmListResultVO result = new RankFilmListResultVO();
            result.setScore(film.getFilmScore());
            result.setImgAddress(film.getImgAddress());
            result.setFilmName(film.getFilmName());
            result.setFilmId(film.getUuid().toString());
            result.setExpectNum(film.getFilmPresalenum().toString());
            result.setBoxNum(film.getFilmBoxOffice().toString());

            results.add(result);
        });

        return results;
    }

    /*
        期待观影人数排行 - 即将上映
     */
    @Override
    public List<RankFilmListResultVO> expectRandFilms() throws CommonServiceException {
        Page<FilmInfoT> page = new Page<>(1,10);
        page.setDesc("film_preSaleNum");
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("film_status","2");

        IPage<FilmInfoT> iPage = filmInfoTMapper.selectPage(page, queryWrapper);

        List<RankFilmListResultVO> results = Lists.newArrayList();

        iPage.getRecords().stream().forEach((film)->{
            RankFilmListResultVO result = new RankFilmListResultVO();
            result.setScore(film.getFilmScore());
            result.setImgAddress(film.getImgAddress());
            result.setFilmName(film.getFilmName());
            result.setFilmId(film.getUuid().toString());
            result.setExpectNum(film.getFilmPresalenum().toString());
            result.setBoxNum(film.getFilmBoxOffice().toString());

            results.add(result);
        });
        return results;
    }

    /*
        评分排行 - 正在热映的电影
     */
    @Override
    public List<RankFilmListResultVO> topRandFilms() throws CommonServiceException {
        Page<FilmInfoT> page = new Page<>(1,10);
        page.setDesc("film_score");
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("film_status","1");

        IPage<FilmInfoT> iPage = filmInfoTMapper.selectPage(page, queryWrapper);

        List<RankFilmListResultVO> results = Lists.newArrayList();

        iPage.getRecords().stream().forEach((film)->{
            RankFilmListResultVO result = new RankFilmListResultVO();
            result.setScore(film.getFilmScore());
            result.setImgAddress(film.getImgAddress());
            result.setFilmName(film.getFilmName());
            result.setFilmId(film.getUuid().toString());
            result.setExpectNum(film.getFilmPresalenum().toString());
            result.setBoxNum(film.getFilmBoxOffice().toString());

            results.add(result);
        });
        return results;
    }

    @Override
    public String checkCondition(String conditionId, FilmEnum filmEnum) throws CommonServiceException {
        switch (filmEnum){
            case source:
                if ("99".equals(conditionId)) return "99";
                FilmSourceDictT filmSourceDictT = sourceDictTMapper.selectById(conditionId);
                if (filmSourceDictT!=null && ToolUtils.isNotEmpty(filmSourceDictT.getUuid().toString())){
                    return conditionId;
                }else {
                    return "99";
                }
            case year:
                if ("99".equals(conditionId)) return "99";
                FilmYearDictT filmYearDictT = yearDictTMapper.selectById(conditionId);
                if (filmYearDictT!=null && ToolUtils.isNotEmpty(filmYearDictT.getUuid().toString())){
                    return conditionId;
                }else {
                    return "99";
                }
            case cat:
                if ("99".equals(conditionId)) return "99";
                FilmCatDictT filmCatDictT = catDictTMapper.selectById(conditionId);
                if (filmCatDictT!=null && ToolUtils.isNotEmpty(filmCatDictT.getUuid().toString())){
                    return conditionId;
                }else {
                    return "99";
                }
            default:
                throw new CommonServiceException(404,"invalid conditionType!!");
        }

    }

    @Override
    public List<CatInfoResultVO> describeCatInfos(String catId) throws CommonServiceException {
        List<FilmCatDictT> catDictTS = catDictTMapper.selectList(null);

        List<CatInfoResultVO> results = catDictTS.stream().map((data) -> {
            CatInfoResultVO catInfoResultVO = new CatInfoResultVO();
            catInfoResultVO.setCatName(data.getShowName());
            catInfoResultVO.setCatId(data.getUuid().toString());
            if (catId.equals(data.getUuid().toString())) {
                catInfoResultVO.setIsActive("true");
            } else {
                catInfoResultVO.setIsActive("false");
            }
            return catInfoResultVO;
        }).collect(Collectors.toList());

        return results;
    }

    @Override
    public List<SourceInfoResultVO> describeSourceInfos(String sourceId) throws CommonServiceException {
        List<FilmSourceDictT> sourceDictTS = sourceDictTMapper.selectList(null);

        List<SourceInfoResultVO> results = sourceDictTS.stream().map((data) -> {
            SourceInfoResultVO sourceInfoResultVO = new SourceInfoResultVO();
            sourceInfoResultVO.setSourceName(data.getShowName());
            sourceInfoResultVO.setSourceId(data.getUuid().toString());
            if (sourceId.equals(data.getUuid().toString())) {
                sourceInfoResultVO.setIsActive("true");
            } else {
                sourceInfoResultVO.setIsActive("false");
            }
            return sourceInfoResultVO;
        }).collect(Collectors.toList());

        return results;
    }

    @Override
    public List<YearInfoResultVO> describeYearInfos(String yearId) throws CommonServiceException {
        List<FilmYearDictT> yearDictTS = yearDictTMapper.selectList(null);

        List<YearInfoResultVO> results = yearDictTS.stream().map(data -> {
            YearInfoResultVO yearInfoResultVO = new YearInfoResultVO();
            yearInfoResultVO.setYearName(data.getShowName());
            yearInfoResultVO.setYearId(data.getUuid().toString());
            if(yearId.equals(data.getUuid().toString())){
                yearInfoResultVO.setIsActive("true");
            }else{
                yearInfoResultVO.setIsActive("false");
            }
            return yearInfoResultVO;
        }).collect(Collectors.toList());

        return results;
    }

    @Override
    public IPage<FilmInfoT> describeFilms(DescribeFilmListReqVO filmListReqVO) throws CommonServiceException {

        Page<FilmInfoT> infoTPage = new Page<FilmInfoT>(Long.parseLong(filmListReqVO.getNowPage()),Long.parseLong(filmListReqVO.getPageSize()));
        // 排序方式 1-按热门搜索，2-按时间搜索，3-按评价搜索
        Map<String ,String > sortMap = Maps.newHashMap();
        sortMap.put("1","film_preSaleNum");
        sortMap.put("2","film_time");
        sortMap.put("3","film_score");
        // hashMap搜索的时间复杂度是 log0
        infoTPage.setDesc(sortMap.get(filmListReqVO.getSortId()));

        QueryWrapper queryWrapper = new QueryWrapper();
        // 判断待搜索列表内容  1-正在热映，2-即将上映，3-经典影片
        queryWrapper.eq("film_source",filmListReqVO.getShowType());

        // 组织QueryWrapper的内容
        if (!"99".equals(filmListReqVO.getSourceId())){
            queryWrapper.eq("film_source",filmListReqVO.getSourceId());
        }
        if (!"99".equals(filmListReqVO.getYearId())){
            queryWrapper.eq("film_date",filmListReqVO.getYearId());
        }
        // #3#2#12   #1# 11 111
        if (!"99".equals(filmListReqVO.getCatId())){
            queryWrapper.like("film_cats","#"+filmListReqVO.getCatId()+"#");
        }

        IPage<FilmInfoT> iPage = filmInfoTMapper.selectPage(infoTPage, queryWrapper);

        return iPage;
    }

    @Override
    public FilmDetailResultVO describeFilmDetails(String searchStr, String searchType) throws CommonServiceException {
        FilmDetailResultVO result;
        //0表示按照编号查找，1表示按照名称查找
        if ("0".equals(searchType)){
            result = filmInfoTMapper.describeFilmDetailByFilmId(searchStr);
        }else {
            result = filmInfoTMapper.describeFilmDetailByFilmName(searchStr);
        }

        return result;
    }

    @Override
    public String describeFilmBiography(String filmId) throws CommonServiceException {

        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("film_id",filmId);

        String biography = "";

//        List<FilmDetailT> list = filmDetailTMapper.selectList(queryWrapper);
//        if (list!=null && list.size()>0){
//            FilmDetailT filmDetailT = list.get(0);
//            biography = filmDetailT.getBiography();
//        }
        Page<FilmDetailT> infoTPage = new Page<FilmDetailT>();
        IPage<FilmDetailT> iPage = filmDetailTMapper.selectPage(infoTPage, queryWrapper);
        if(iPage.getRecords()!=null && iPage.getRecords().size()>0){
            FilmDetailT filmDetailT = iPage.getRecords().get(0);
            biography = filmDetailT.getBiography();
        }
        return biography;
    }

    @Override
    public ImagesResultVO describeFilmImages(String filmId) throws CommonServiceException {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("film_id",filmId);

        ImagesResultVO imagesResultVO = new ImagesResultVO();

        List<FilmDetailT> list = filmDetailTMapper.selectList(queryWrapper);
        if (list!=null && list.size()>0){
            FilmDetailT filmDetailT = list.get(0);

            String[] images = filmDetailT.getFilmImgs().split(",");

            // 验证images是否存在同时是不是五个
            if (images.length>0){
                imagesResultVO.setMainImg(images[0]);
                if(images.length>1){
                    imagesResultVO.setImg01(images[1]);
                    if(images.length>2){
                        imagesResultVO.setImg02(images[2]);
                        if(images.length>3){
                            imagesResultVO.setImg03(images[3]);
                            if(images.length>4){
                                imagesResultVO.setImg04(images[4]);
                            }
                        }
                    }
                }
            }

        }

        return imagesResultVO;
    }

    @Override
    public ActorResultVO describeDirector(String filmId) throws CommonServiceException {


        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("film_id",filmId);

        String directorId = "";
        ActorResultVO resultVO = new ActorResultVO();

        List<FilmDetailT> list = filmDetailTMapper.selectList(queryWrapper);
        if (list!=null && list.size()>0) {
            FilmDetailT filmDetailT = list.get(0);
            directorId = filmDetailT.getDirectorId().toString();
        }
        // 根据filmId获取导演的编号
        // 导演编号获取对应的导演信息

        if(ToolUtils.isNotEmpty(directorId)){
            FilmActorT filmActorT = actorTMapper.selectById(directorId);
            resultVO.setDirectorName(filmActorT.getActorName());
            resultVO.setImgAddress(filmActorT.getActorImg());
        }

        return resultVO;
    }

    @Override
    public List<ActorResultVO> describeActors(String filmId) throws CommonServiceException {
        List<ActorResultVO> results = actorTMapper.describeActorsByFilmId(filmId);
        return results;
    }
}
