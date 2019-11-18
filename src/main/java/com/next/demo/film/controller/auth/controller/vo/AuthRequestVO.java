package com.next.demo.film.controller.auth.controller.vo;

import com.next.demo.film.common.utils.ToolUtils;
import com.next.demo.film.controller.common.BaseVO;
import com.next.demo.film.controller.exception.ParamErrorException;
import lombok.Data;

@Data
public class AuthRequestVO extends BaseVO {

    private String username;
    private String password;

    @Override
    public void checkParam() throws ParamErrorException {
        if(ToolUtils.isEmpty(this.getUsername())){
            throw new ParamErrorException(400,"用户名不能为空");
        }

        if(ToolUtils.isEmpty(this.getPassword())){
            throw new ParamErrorException(400,"密码不能为空");
        }
    }
}
