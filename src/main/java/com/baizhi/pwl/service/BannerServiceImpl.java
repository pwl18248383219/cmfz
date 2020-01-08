package com.baizhi.pwl.service;

import com.baizhi.pwl.dao.BannerDao;
import com.baizhi.pwl.entity.Banner;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Component
@Transactional

public class BannerServiceImpl implements BannerService {

    @Autowired
    BannerDao bannerDao;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public List<Banner> queryByPage(Banner banner,Integer page,Integer rows) {
        RowBounds rowBounds = new RowBounds((page-1)*rows,rows);
        List<Banner> banners = bannerDao.selectByRowBounds(banner, rowBounds);
        return banners;
    }


    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public Banner queryOne(Banner banner) {
        Banner banner1 = bannerDao.selectOne(banner);
        return banner1;
    }

    @Override
    public List<Banner> queryAll() {
        List<Banner> banners = bannerDao.selectAll();
        return banners;
    }

    @Override
    public void add(Banner banner) {

        bannerDao.insertSelective(banner);
    }

    @Override
    public void modify(Banner banner) {
        bannerDao.updateByPrimaryKeySelective(banner);
    }

    @Override
    public void remove(String[] id) {
        bannerDao.deleteByIdList(Arrays.asList(id));
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public Integer queryCount() {
        int count = bannerDao.selectCount(null);
        return count;
    }

    @Override
    public List<Banner> queryBannersByTime() {
        List<Banner> banners = bannerDao.queryBannersByTime();
        return banners;
    }
}
