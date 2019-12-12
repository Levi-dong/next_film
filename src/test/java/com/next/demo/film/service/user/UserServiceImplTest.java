package com.next.demo.film.service.user;

import com.next.demo.film.controller.user.vo.EnrollUserVO;
import com.next.demo.film.controller.user.vo.UserInfoVO;
import com.next.demo.film.service.common.exception.CommonServiceException;
import org.apache.catalina.filters.ExpiresFilter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserServiceImplTest {

    @Autowired
    private UserServiceAPI userServiceAPI;

    @Test
    public void userEnroll() throws CommonServiceException {
        EnrollUserVO enrollUserVO = new EnrollUserVO();
        enrollUserVO.setUsername("allen");
        enrollUserVO.setPassword("111");
        enrollUserVO.setAddress("东京191");
        enrollUserVO.setEmail("792489725@qq.com");
        enrollUserVO.setPhone("17854653215");

        userServiceAPI.userEnroll(enrollUserVO);
    }

    @Test
    public void checkUserName() {
    }

    @Test
    public void userAuth() {
    }

    @Test
    public void describeUserInfo() throws CommonServiceException {
        String userId = "5";
        System.out.println(userServiceAPI.describeUserInfo(userId));
    }

    @Test
    public void updateUserInfo() throws CommonServiceException {
        UserInfoVO userInfoVO = new UserInfoVO();
        userInfoVO.setUuid(6);
        userInfoVO.setUsername("next");
        userInfoVO.setLifeState("0");

        System.out.println(userServiceAPI.updateUserInfo(userInfoVO));
    }
}
