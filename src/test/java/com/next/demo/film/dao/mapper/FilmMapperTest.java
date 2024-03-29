package com.next.demo.film.dao.mapper;

import com.next.demo.film.controller.film.vo.response.filmdetail.ActorResultVO;
import com.next.demo.film.controller.film.vo.response.filmdetail.FilmDetailResultVO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest
public class FilmMapperTest {

    @Autowired
    private FilmInfoTMapper filmInfoTMapper;

    @Autowired
    private FilmActorTMapper actorTMapper;

    @Test
    public void describeFilmInfoById(){
        String filmId = "2";
        FilmDetailResultVO filmDetailResultVO = filmInfoTMapper.describeFilmDetailByFilmId(filmId);
        System.out.println("filmDetailResultVO by Id:"+filmDetailResultVO);
    }

    @Test
    public void describeFilmInfoByName(){
        String filmName = "药神";
        FilmDetailResultVO filmDetailResultVO = filmInfoTMapper.describeFilmDetailByFilmName(filmName);
        System.out.println("filmDetailResultVO by Id:"+filmDetailResultVO);
    }

    @Test
    public void describeActorById(){
        String filmId = "2";
        List<ActorResultVO> actors = actorTMapper.describeActorsByFilmId(filmId);
        actors.stream().forEach(
                //(actor)-> System.out.println(actor)
                System.out::println
        );
    }

}
