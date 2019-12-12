package com.next.demo.film.test;

import com.google.common.collect.Lists;
import com.next.demo.film.dao.entity.NextUser;
import org.junit.Test;

import java.util.List;
import java.util.Optional;

public class TestOptional {

    @Test
    public void test1(){
        List<NextUser> list = Lists.newArrayList();
        //Optional<NextUser> op = Optional.of(new NextUser());
        //Optional<NextUser> op = Optional.of(null);
        //Optional<List<NextUser>> op = Optional.of(list);
        Optional<NextUser> op = Optional.ofNullable(null);
        //Optional<NextUser> op = Optional.ofNullable(new NextUser());
        //System.out.println(op.get());
        System.out.println(op.isPresent());

    }
}
