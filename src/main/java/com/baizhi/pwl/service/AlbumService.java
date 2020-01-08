package com.baizhi.pwl.service;


import com.baizhi.pwl.entity.Album;

import java.util.List;
import java.util.Map;

public interface AlbumService {
    //查询总行数
    public Integer queryCount(Album album);
    //分页查
    public List<Album> queryByPage(Album album, Integer page, Integer rows);
    //根据条件查一个
    public Album queryOne(String id);
    //添加
    public void add(Album album);
    //修改
    public void modify(Album album);
    //删除
    public void remove(String[] id);
}
