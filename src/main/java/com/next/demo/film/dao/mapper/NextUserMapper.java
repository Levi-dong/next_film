package com.next.demo.film.dao.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.next.demo.film.dao.entity.NextUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Levi
 * @since 2019-11-03
 */
public interface NextUserMapper extends BaseMapper<NextUser> {

    List<NextUser> getUsers();
    List<NextUser> getUsers(IPage<NextUser> iPage);

}
