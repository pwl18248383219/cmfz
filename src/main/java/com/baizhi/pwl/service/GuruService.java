package com.baizhi.pwl.service;

import com.baizhi.pwl.entity.Chapter;
import com.baizhi.pwl.entity.Guru;

import java.util.List;


public interface GuruService {


    //查所有
    public List<Guru> queryAll();
    //添加
    public void add(Guru guru);
    //修改
    public void modify(Guru guru);
    //删除
    public void remove(String[] id);
}
