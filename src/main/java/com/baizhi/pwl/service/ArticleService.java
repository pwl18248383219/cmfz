package com.baizhi.pwl.service;

import com.baizhi.pwl.entity.Article;

import java.util.List;

public interface ArticleService {

    //查询总行数
    public Integer queryCount(Article article);
    //分页查
    public List<Article> queryByPage(Article article, Integer page, Integer rows);
    //查所有
    public List<Article> queryAll();
    //根据条件查一个
    public Article queryOne(String id);
    //添加
    public void add(Article article);
    //修改
    public void modify(Article article);
    //删除
    public void remove(String[] id);
}
