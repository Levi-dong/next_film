package com.next.demo.film.service.user;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.next.demo.film.common.utils.MD5Util;
import com.next.demo.film.common.utils.ToolUtils;
import com.next.demo.film.controller.common.TraceUtil;
import com.next.demo.film.controller.user.vo.EnrollUserVO;
import com.next.demo.film.controller.user.vo.UserInfoVO;
import com.next.demo.film.dao.entity.NextUser;
import com.next.demo.film.dao.entity.NextUserT;
import com.next.demo.film.dao.mapper.NextUserTMapper;
import com.next.demo.film.service.common.exception.CommonServiceException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;

@Service("userService")
public class UserServiceImpl implements UserServiceAPI{

    @Autowired
    private NextUserTMapper userTMapper;

    @Override
    public void userEnroll(EnrollUserVO enrollUserVO) throws CommonServiceException {
        //校验

        //EnrollUserVO -> NextUserT 转换
        NextUserT nextUserT = new NextUserT();
        //nextUserT.setEmail(enrollUserVO.getEmail());

        //springboot的工具类 属性复制 变量名称一致
        BeanUtils.copyProperties(enrollUserVO,nextUserT);
        //变量名称不一致 需要手动
        nextUserT.setUserName(enrollUserVO.getUsername());
        nextUserT.setUserPwd(MD5Util.encrypt(enrollUserVO.getPassword()));

        //数据插入
        int isSuccess = userTMapper.insert(nextUserT);

        //判断插入是否成功
        if(isSuccess!=1){
            throw new CommonServiceException(501,"用户登记失败");
        }
    }

    @Override
    public boolean checkUserName(String userName) throws CommonServiceException {
        //Optional.ofNullable(userName).orElseThrow(CommonServiceException::new);

        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("user_name",userName);

        int hasUserName = userTMapper.selectCount(queryWrapper);
        return hasUserName == 0 ? false : true;
    }

    @Override
    public boolean userAuth(String userName, String userPwd) throws CommonServiceException {
        if(ToolUtils.isEmpty(userName)||ToolUtils.isEmpty(userPwd)) {
            throw new CommonServiceException(400, "用户登记失败！");
        }

        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("user_name",userName);

        //1.判断用户名是否存在
        List<NextUserT> list = userTMapper.selectList(queryWrapper);
        if(list.size()==0){
            return false;
        }else{
            //2.如果存在之判断密码是否正确
            NextUserT nextUserT = list.get(0);
            //3.对用户输入的密码进行MD5加密,然后判断两个密码是否相等
            if (MD5Util.encrypt(userPwd).equals(nextUserT.getUserPwd())){
                return true;
            }
        }
        return false;
    }

    @Override
    public UserInfoVO describeUserInfo(String userId) throws CommonServiceException {
        NextUserT nextUserT = userTMapper.selectById(userId);
        if (nextUserT !=null && nextUserT.getUuid()!=null){
            UserInfoVO userInfoVO = user2infoVO(nextUserT);
            return userInfoVO;
        }else{
            throw new CommonServiceException(404, "用户编号为["+userId+"]的用户不存在");
        }
    }

    @Override
    public UserInfoVO updateUserInfo(UserInfoVO userInfoVO) throws CommonServiceException {
        NextUserT nextUserT = info2user(userInfoVO);
        if (nextUserT !=null && nextUserT.getUuid()!=null){
            int isSuccess = userTMapper.updateById(nextUserT);
            if(isSuccess==1){
                UserInfoVO result = describeUserInfo(userInfoVO.getUuid()+"");
                return result;
            }else{
                throw new CommonServiceException(500, "用户修改信息失败");
            }
        }else{
            throw new CommonServiceException(404, "用户编号为["+userInfoVO.getUuid()+"]的用户不存在");
        }
    }

    @Override
    public void saveUserIdToken(String userName){
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("user_name",userName);
        List<NextUserT> list = userTMapper.selectList(queryWrapper);
        if(list!=null && list.size()>0){
            NextUserT nextUserT = list.get(0);
            TraceUtil.initThread(nextUserT.getUuid()+"");
        }
    }


    private UserInfoVO user2infoVO(NextUserT nextUserT){
        UserInfoVO userInfoVO = new UserInfoVO();
        userInfoVO.setUsername(nextUserT.getUserName());
        userInfoVO.setNickname(nextUserT.getNickName());

        userInfoVO.setBeginTime(nextUserT.getBeginTime().toEpochSecond(ZoneOffset.of("+8")));
        userInfoVO.setUpdateTime(nextUserT.getUpdateTime().toEpochSecond(ZoneOffset.of("+8")));

        userInfoVO.setLifeState(nextUserT.getLifeState()+"");

        BeanUtils.copyProperties(nextUserT,userInfoVO);

        return userInfoVO;

    }

    private NextUserT info2user(UserInfoVO userInfoVO){
        NextUserT nextUserT = new NextUserT();
        nextUserT.setUserName(userInfoVO.getUsername());
        nextUserT.setNickName(userInfoVO.getNickname());

        nextUserT.setBeginTime(LocalDateTime.now());

        if(Optional.ofNullable(userInfoVO.getLifeState()).isPresent()){
            nextUserT.setLifeState(Integer.parseInt(userInfoVO.getLifeState()));
        }

        BeanUtils.copyProperties(userInfoVO, nextUserT);
        return nextUserT;
    }


}
