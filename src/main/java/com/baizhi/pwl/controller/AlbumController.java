package com.baizhi.pwl.controller;


import com.baizhi.pwl.entity.Album;
import com.baizhi.pwl.entity.Banner;
import com.baizhi.pwl.entity.Chapter;
import com.baizhi.pwl.service.AlbumService;
import com.baizhi.pwl.service.ChapterService;
import com.baizhi.pwl.util.HttpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/album")
public class AlbumController {
    @Autowired
    AlbumService albumService;
    @Autowired
    ChapterService chapterService;

    @RequestMapping("/queryByPage")
    public Map queryByPage(Integer page,Integer rows){
        HashMap hashMap = new HashMap();
        //分页查询到的数据
        List<Album> albums = albumService.queryByPage(null, page, rows);
        //查询总行数
        Integer records = albumService.queryCount(null);
        //计算总页数
        Integer total =  records % rows == 0 ? records / rows : records / rows + 1;
        hashMap.put("page", page);
        hashMap.put("rows", albums);
        hashMap.put("total", total);
        hashMap.put("records", records);
        return hashMap;
    }

    @RequestMapping("/editAlbum")
    public Map editAlbum(String oper, Album album, String[] id) {
        HashMap hashMap = new HashMap();

        //添加
        if (oper.equals("add")) {
            String uid = UUID.randomUUID().toString();
            album.setId(uid);
            albumService.add(album);
            hashMap.put("albumId", uid);
        } else if (oper.equals("edit")) {//修改
            albumService.modify(album);
            hashMap.put("albumId", album.getId());
        } else {//删除
            albumService.remove(id);

        }
        return hashMap;
    }

    @RequestMapping("/uploadAlbum")
    public Map uploadAlbum(MultipartFile cover, String albumId, HttpSession session, HttpServletRequest request) {
        HashMap hashMap = new HashMap();
        //获取真是路径
        String realPath = session.getServletContext().getRealPath("/upload/albumImg/");
        //判断文件夹是否存在
        File file = new File(realPath);
        if (!file.exists()) {
            file.mkdirs();
        }
        String uri = HttpUtil.getHttp(cover, request, "/upload/albumImg/");
        Album album = new Album();
        album.setId(albumId);
        album.setCover(uri);
        albumService.modify(album);
        hashMap.put("status", 200);
        return hashMap;
    }


    //7.专辑详情接口
    @RequestMapping("/albumMsg")
    public Map albumMsg(String uid,String id){
        HashMap hashMap = new HashMap();
        Album album = albumService.queryOne(id);
        List<Chapter> chapters = chapterService.queryByAlbumId(id);
        hashMap.put("status","200");
        hashMap.put("album",album);
        hashMap.put("list",chapters);
        return hashMap;
    }
}
