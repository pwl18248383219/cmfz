package com.baizhi.pwl.service;

import com.baizhi.pwl.entity.Chapter;

import java.util.List;

public interface ChapterService {

    //分页查
    public List<Chapter> queryByPage(Integer page,Integer rows,String albumId);
    //查询总行数
    public Integer queryCount(String albumId);
    //根据文章查
    public List<Chapter> queryByAlbumId(String albumId);
    //添加
    public void add(Chapter chapter);
    //修改
    public void modify(Chapter chapter);
    //删除
    public void remove(String[] id);

}
