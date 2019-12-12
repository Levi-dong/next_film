package com.next.demo.film.controller.common;

import io.swagger.models.auth.In;
import lombok.Data;

@Data
public final class BaseResponseVO<M> {

    private BaseResponseVO(){}

    // 返回状态【0-成功，1-业务失败，999-表示系统异常】
    private int status;
    // 返回信息
    private String msg;

    // 返回数据实体;
    private M data;

    // 图片前缀
    private String imgPre;

    // 分页使用
    private Long nowPage;
    private Long totalPage;

    public static<M> BaseResponseVO success(){
        BaseResponseVO baseResponseVO = new BaseResponseVO();
        baseResponseVO.setStatus(0);
        return baseResponseVO;
    }

    public static<M> BaseResponseVO success(String msg){
        BaseResponseVO baseResponseVO = new BaseResponseVO();
        baseResponseVO.setStatus(0);
        baseResponseVO.setMsg(msg);
        return baseResponseVO;
    }

    public static<M> BaseResponseVO success(M data){
        BaseResponseVO baseResponseVO = new BaseResponseVO();
        baseResponseVO.setStatus(0);
        baseResponseVO.setData(data);
        return baseResponseVO;
    }

    public static<M> BaseResponseVO success(Long nowPage,Long totalPage,M m){
        BaseResponseVO baseResponseVO = new BaseResponseVO();
        baseResponseVO.setStatus(0);
        baseResponseVO.setData(m);
        baseResponseVO.setTotalPage(totalPage);
        baseResponseVO.setNowPage(nowPage);

        return baseResponseVO;
    }

    public static<M> BaseResponseVO success(Long nowPage,Long totalPage,String imgPre,M m){
        BaseResponseVO baseResponseVO = new BaseResponseVO();
        baseResponseVO.setStatus(0);
        baseResponseVO.setData(m);
        baseResponseVO.setImgPre(imgPre);
        baseResponseVO.setTotalPage(totalPage);
        baseResponseVO.setNowPage(nowPage);

        return baseResponseVO;
    }

    public static<M> BaseResponseVO success(String imgPre,M m){
        BaseResponseVO baseResponseVO = new BaseResponseVO();
        baseResponseVO.setStatus(0);
        baseResponseVO.setData(m);
        baseResponseVO.setImgPre(imgPre);

        return baseResponseVO;
    }

    public static<M> BaseResponseVO noLogin(){
        BaseResponseVO baseResponseVO = new BaseResponseVO();
        baseResponseVO.setStatus(700);
        baseResponseVO.setMsg("用户需要登陆");

        return baseResponseVO;
    }

    public static<M> BaseResponseVO serviceFailed(String msg){
        BaseResponseVO baseResponseVO = new BaseResponseVO();
        baseResponseVO.setStatus(1);
        baseResponseVO.setMsg(msg);
        return baseResponseVO;
    }

    public static<M> BaseResponseVO serviceFailed(String msg,M data){
        BaseResponseVO baseResponseVO = new BaseResponseVO();
        baseResponseVO.setStatus(1);
        baseResponseVO.setMsg(msg);
        baseResponseVO.setData(data);
        return baseResponseVO;
    }

    public static<M> BaseResponseVO serviceFailed(int status,String msg){
        BaseResponseVO baseResponseVO = new BaseResponseVO();
        baseResponseVO.setStatus(status);
        baseResponseVO.setMsg(msg);
        return baseResponseVO;
    }

    public static<M> BaseResponseVO systemError(){
        BaseResponseVO baseResponseVO = new BaseResponseVO();
        baseResponseVO.setStatus(999);
        baseResponseVO.setMsg("系统异常，请联系管理员");
        return baseResponseVO;
    }

}
