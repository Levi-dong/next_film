package com.next.demo.film.controller.user;

import com.next.demo.film.common.utils.ToolUtils;
import com.next.demo.film.controller.common.BaseResponseVO;
import com.next.demo.film.controller.common.TraceUtil;
import com.next.demo.film.controller.exception.NextFilmException;
import com.next.demo.film.controller.exception.ParamErrorException;
import com.next.demo.film.controller.user.vo.EnrollUserVO;
import com.next.demo.film.controller.user.vo.UserInfoVO;
import com.next.demo.film.service.common.exception.CommonServiceException;
import com.next.demo.film.service.user.UserServiceAPI;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
//@Controller+@ResponseBody = @RestController
//@RequestMapping(value="/nextfilm/user/")

//体现swagger2跟别的请求  请求去掉nextfilm
@RequestMapping(value="/user/")
@Api("用户模块相关API")
public class UserControllerForSwagger2 {

    @Autowired
    private UserServiceAPI userServiceAPI;

//    @ResponseBody
    @RequestMapping(value="check",method = RequestMethod.POST)
    @ApiOperation(value="用户名重复性验证",notes="用户名重复性验证")
    @ApiImplicitParam(name = "username",value="待验证的用户名称",paramType="query",required = true,dataType = "string")
    public BaseResponseVO checkUser(String username) throws CommonServiceException, NextFilmException {

/*        if(ToolUtils.isEmpty(username)){
            throw new NextFilmException(1,"username不能为空");
        }*/
        if(ToolUtils.isEmpty(username)){
            throw new CommonServiceException(400,"username不能为空");
        }

//        if (Optional.ofNullable(username).isPresent()) {
//            throw new NextFilmException(1,"username不能为null");
//        }

        boolean hasUserName = userServiceAPI.checkUserName(username);

        if (hasUserName) {
            return BaseResponseVO.serviceFailed("用户名已存在");
        }else{
            return BaseResponseVO.success();
        }
    }


    @RequestMapping(value="register",method = RequestMethod.POST)
    public BaseResponseVO register(@RequestBody EnrollUserVO enrollUserVO) throws CommonServiceException, NextFilmException, ParamErrorException {
        //领域模型 贫血模型
        enrollUserVO.checkParam();

        userServiceAPI.userEnroll(enrollUserVO);

        return BaseResponseVO.success();
    }


    @RequestMapping(value="getUserInfo",method = RequestMethod.GET)
    public BaseResponseVO describeUserInfo() throws CommonServiceException, ParamErrorException {
        String userId = TraceUtil.getUserId();
        UserInfoVO userInfoVO = userServiceAPI.describeUserInfo(userId);
        userInfoVO.checkParam();
        return BaseResponseVO.success(userInfoVO);
    }

    @RequestMapping(value="updateUserInfo",method = RequestMethod.POST)
    public BaseResponseVO updateUserInfo(@RequestBody UserInfoVO userInfoVO) throws CommonServiceException, ParamErrorException {
        userInfoVO.checkParam();
        UserInfoVO result = userServiceAPI.updateUserInfo(userInfoVO);
        userInfoVO.checkParam();
        return BaseResponseVO.success(result);
    }


    @RequestMapping(value="logout",method = RequestMethod.POST)
    public BaseResponseVO logout() throws CommonServiceException, ParamErrorException {

        String userId = TraceUtil.getUserId();
        /**
         * 1.用户信息放入Redis缓存
         * 2.去掉用户缓存
         */


        return BaseResponseVO.success();
    }
}
