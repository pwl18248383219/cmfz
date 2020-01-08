package com.baizhi.pwl.dao;

import com.baizhi.pwl.entity.MapDto;
import com.baizhi.pwl.entity.User;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.additional.idlist.DeleteByIdListMapper;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface UserDao extends Mapper<User>, DeleteByIdListMapper<User,String> {
    Integer selectUserByTime(@Param("sex") String sex, @Param("day") Integer day);

    List<MapDto> queryBySexGetLocation(String sex);
}
