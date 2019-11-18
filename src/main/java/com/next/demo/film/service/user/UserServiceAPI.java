package com.next.demo.film.service.user;

import com.next.demo.film.controller.user.vo.EnrollUserVO;
import com.next.demo.film.controller.user.vo.UserInfoVO;
import com.next.demo.film.service.common.exception.CommonServiceException;

public interface UserServiceAPI {

    /*
     用户登记接口
     */
    void userEnroll(EnrollUserVO enrollUserVO) throws CommonServiceException;

    /*
    验证用户名是否存在
     */

    boolean checkUserName(String userName) throws CommonServiceException;

    /*
    用户名密码验证
     */

    boolean userAuth(String userName,String userPwd) throws CommonServiceException;

    /*
    获取用户信息
     */
    UserInfoVO describeUserInfo(String userId) throws CommonServiceException;

    /*
    修改用户信息
     */
    UserInfoVO updateUserInfo(UserInfoVO userInfoVO) throws CommonServiceException;

    /*
    存入token
     */
    void saveUserIdToken(String userName);
}
