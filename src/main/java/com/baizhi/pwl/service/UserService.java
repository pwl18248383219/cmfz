package com.baizhi.pwl.service;

import com.baizhi.pwl.entity.MapDto;
import com.baizhi.pwl.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface UserService {

    //分页查所有
    public List<User> queryByPage(User user, Integer page, Integer rows);

    //查询总条数
    public Integer queryCount();

    //查一个
    public User queryOne(User user);

    //添加
    public void add(User user);

    //修改
    public Map modify(User user);

    //删除
    public void remove(String[] id);

    //活跃度查询
    public Integer queryUserByTime(String sex,Integer day);

    public List<MapDto> queryBySexGetLocation(String sex);

    public Map login(String phone,String password);
}
