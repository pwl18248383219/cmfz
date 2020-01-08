package com.baizhi.pwl.service;

import com.baizhi.pwl.entity.Banner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface BannerService {


    //分页查所有
    public List<Banner> queryByPage(Banner banner,Integer page,Integer rows);

    //查一个
    public Banner queryOne(Banner banner);

    //查所有
    public List<Banner> queryAll();

    //添加
    public void add(Banner banner);

    //修改
    public void modify(Banner banner);

    //删除
    public void remove(String[] id);

    //查询总条数
    public Integer queryCount();


    public List<Banner> queryBannersByTime();
}
