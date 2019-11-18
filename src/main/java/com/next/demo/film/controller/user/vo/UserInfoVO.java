package com.next.demo.film.controller.user.vo;

import com.next.demo.film.common.utils.ToolUtils;
import com.next.demo.film.controller.common.BaseVO;
import com.next.demo.film.controller.exception.ParamErrorException;
import lombok.Data;

@Data
public class UserInfoVO extends BaseVO {
    private Integer id;
    private Integer uuid;
    private String username;
    private String nickname;
    private String email;
    private String phone;
    private Integer sex;
    private String birthday;
    private String lifeState;
    private String biography;
    private String address;
    private String headAddress;
    private Long beginTime;
    private Long updateTime;

    public Integer getUuid(){
        return this.getId();
    }

    @Override
    public void checkParam() throws ParamErrorException {
        if(ToolUtils.isEmpty(this.getUsername())){
            throw new ParamErrorException(400,"用户名不能为空");
        }
    }
}
