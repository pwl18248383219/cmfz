package com.baizhi.pwl.entity;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baizhi.pwl.dao.BannerDao;
import com.baizhi.pwl.util.MyWebAware;

import java.util.ArrayList;

public class BannerDataListener extends AnalysisEventListener<Banner> {

    ArrayList arrayList = new ArrayList();

    @Override
    public void invoke(Banner banner, AnalysisContext analysisContext) {
        arrayList.add(banner);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        BannerDao bannerDao = (BannerDao) MyWebAware.getBeanByClass(BannerDao.class);
        bannerDao.insertList(arrayList);
    }
}