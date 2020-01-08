package com.baizhi.pwl.controller;

import com.baizhi.pwl.dao.AlbumDao;
import com.baizhi.pwl.dao.ArticleDao;
import com.baizhi.pwl.dao.BannerDao;
import com.baizhi.pwl.entity.Album;
import com.baizhi.pwl.entity.Article;
import com.baizhi.pwl.entity.Banner;
import com.baizhi.pwl.service.AlbumService;
import com.baizhi.pwl.service.ArticleService;
import com.baizhi.pwl.service.BannerService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/onePage")
@RestController
public class OnePageController {
    @Autowired
    BannerService bannerService;
    @Autowired
    AlbumService albumService;
    @Autowired
    ArticleService articleService;
    @RequestMapping("onePage")
    // 5. 一级页面展示接口
    public Map onePage(String uid, String type, String sub_type){
        HashMap hashMap = new HashMap();
        try {
            if (type.equals("all")){
                List<Banner> banners = bannerService.queryBannersByTime();
                List<Album> albums = albumService.queryByPage(null,0,5);
                List<Article> articles = articleService.queryAll();
                hashMap.put("status",200);
                hashMap.put("head",banners);
                hashMap.put("albums",albums);
                hashMap.put("articles",articles);
            }else if (type.equals("wen")){
                List<Album> albums = albumService.queryByPage(null,0,5);
                hashMap.put("status",200);
                hashMap.put("albums",albums);
            }else {
                if (sub_type.equals("ssyj")){
                    List<Article> articles = articleService.queryAll();
                    hashMap.put("status",200);
                    hashMap.put("articles",articles);
                }else {
                    List<Article> articles = articleService.queryAll();
                    hashMap.put("status",200);
                    hashMap.put("articles",articles);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            hashMap.put("status","-200");
            hashMap.put("message","error");
        }

        return hashMap;
    }
}
