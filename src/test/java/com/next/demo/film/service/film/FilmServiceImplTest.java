package com.next.demo.film.service.film;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.next.demo.film.TestAnnotateSuper;
import com.next.demo.film.controller.film.FilmEnum;
import com.next.demo.film.controller.film.vo.request.DescribeFilmListReqVO;
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
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.w3c.dom.ls.LSOutput;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class FilmServiceImplTest extends TestAnnotateSuper {

    @Autowired
    private FilmServiceAPI filmServiceAPI;

    @Test
    public void describeBanners() {

        try {
            List<BannerInfoResultVO> result = filmServiceAPI.describeBanners();
            result.stream().forEach(
                    System.out::println
            );
        } catch (CommonServiceException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void describeHotFilms() {
        try {
            List<HotFilmListResultVO> resultVOS = filmServiceAPI.describeHotFilms();
            resultVOS.stream().forEach(
                    (data)-> System.out.println(data)
                  //  System.out::println
            );
        } catch (CommonServiceException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void describeSoonFilms() throws CommonServiceException {
        List<SoonFilmListResultVO> soonFilmListResultVOS = filmServiceAPI.describeSoonFilms();
        soonFilmListResultVOS.stream().forEach((data)-> System.out.println(data));
    }

    @Test
    public void boxRandFilms() throws CommonServiceException {
        List<RankFilmListResultVO> results = filmServiceAPI.boxRandFilms();
        results.stream().forEach(System.out::println);
    }

    @Test
    public void expectRandFilms() throws CommonServiceException {
        List<RankFilmListResultVO> results = filmServiceAPI.expectRandFilms();
        results.stream().forEach(System.out::println);
    }

    @Test
    public void topRandFilms() throws CommonServiceException {
        List<RankFilmListResultVO> results = filmServiceAPI.topRandFilms();
        results.stream().forEach((data)-> System.out.println(data));
    }

    @Test
    public void checkCondition() throws CommonServiceException {
        String checkCondition = filmServiceAPI.checkCondition("5", FilmEnum.year);
        System.out.println(checkCondition);

    }

    @Test
    public void describeCatInfos() {
    }

    @Test
    public void describeSourceInfos() {
    }

    @Test
    public void describeYearInfos() {
    }

    @Test
    public void describeFilms() throws CommonServiceException {

        DescribeFilmListReqVO resultVO = new DescribeFilmListReqVO();

        IPage<FilmInfoT> filmInfoTIPage = filmServiceAPI.describeFilms(resultVO);

        System.out.println(filmInfoTIPage.getCurrent()+","+filmInfoTIPage.getPages()+","+filmInfoTIPage.getRecords().size());


    }

    @Test
    public void describeFilmDetails() throws CommonServiceException {
        String searchType = "0";
        String searchStr = "药神";
        FilmDetailResultVO resultVO = filmServiceAPI.describeFilmDetails(searchStr, searchType);
        System.out.println(resultVO);
    }

    @Test
    public void describeFilmBio() throws CommonServiceException {
        System.out.println(filmServiceAPI.describeFilmBiography("2"));
    }

    @Test
    public void describeFilmImages() throws CommonServiceException {
        ImagesResultVO imagesResultVO = filmServiceAPI.describeFilmImages("2");
        System.out.println(imagesResultVO);
    }

    @Test
    public void describeDirector() throws CommonServiceException {
        System.out.println(filmServiceAPI.describeDirector("2"));
    }

    @Test
    public void describeActors() throws CommonServiceException {
        List<ActorResultVO> resultVOS = filmServiceAPI.describeActors("2");
        resultVOS.stream().forEach(System.out::println);
        System.out.println("============");
        resultVOS.forEach(System.out::println);
        System.out.println("============");
        resultVOS.stream().map(item->"list:"+item).collect(Collectors.toList()).stream().forEach(System.out::println);

    }
}
