package com.baizhi.pwl.service;

import com.baizhi.pwl.entity.Counter;

import java.util.List;

public interface CounterService {
    //根据条件查
    public List<Counter> queryByUid(String uid,String cid);
    //添加
    public void add(String uid,String title,String cid);
    //删除
    public void remove(String id);
}
