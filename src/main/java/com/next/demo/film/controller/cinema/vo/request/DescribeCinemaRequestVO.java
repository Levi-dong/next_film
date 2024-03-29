package com.next.demo.film.controller.cinema.vo.request;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.next.demo.film.controller.common.BaseVO;
import com.next.demo.film.controller.exception.ParamErrorException;
import com.next.demo.film.dao.entity.FilmCinemaT;
import lombok.Data;

@Data
public class DescribeCinemaRequestVO extends BaseVO {

    private Integer brandId=99;
    private Integer hallType=99;
    private Integer districtId=99;
    private Integer pageSize=20;
    private Integer nowPage=1;

    @Override
    public void checkParam() throws ParamErrorException {

    }
    /*
        获取当前对象相关的Wrapper对象
     */
    public QueryWrapper<FilmCinemaT> genWrapper(){

        QueryWrapper<FilmCinemaT> queryWrapper = new QueryWrapper<>();

        if(this.getBrandId()!=null && this.getBrandId()!=99){
            queryWrapper.eq("brand_id",this.getBrandId());
        }
        if(this.getDistrictId()!=null && this.getDistrictId()!=99){
            queryWrapper.eq("area_id",this.getDistrictId());
        }
        if(this.getHallType()!=null && this.getHallType()!=99){
            queryWrapper.like("hall_ids","#"+this.getHallType()+"#");
        }

        return queryWrapper;
    }
}
