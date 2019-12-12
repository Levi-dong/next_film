package com.next.demo.film.test;

import com.next.demo.film.example.service.bo.RegisterUserBO;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

@Slf4j
public class LombokTest {

//    @Test
//    public void lomboktest(){
//        RegisterUserBO registerUserBO = new RegisterUserBO();
//        registerUserBO.setUsername("username");
//        registerUserBO.setUserpwd("userpwd");
//        registerUserBO.setUuid("001");
//
//        System.out.println("bo="+registerUserBO);
//    }


    @Test
    public void lombokbuildtest(){
        RegisterUserBO registerUserBO = RegisterUserBO.builder()
                .uuid("002")
                .username("admin")
                .userpwd("pwd")
                .build();

        System.out.println("bo="+registerUserBO);
        log.info("registerUserBO="+registerUserBO);
    }
}
