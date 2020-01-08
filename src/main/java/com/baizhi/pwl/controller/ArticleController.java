package com.baizhi.pwl.controller;

import com.baizhi.pwl.entity.Album;
import com.baizhi.pwl.entity.Article;
import com.baizhi.pwl.service.ArticleService;
import com.baizhi.pwl.util.HttpUtil;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    ArticleService articleService;

    @RequestMapping("/uploadImg")
    public Map uploadImg(MultipartFile imgFile,HttpSession session,HttpServletRequest request){
        HashMap hashMap = new HashMap();
        String realPath = session.getServletContext().getRealPath("/upload/articleImg/");
        File file = new File(realPath);
        if(!file.exists()){
            file.mkdirs();
        }
        try {
            String http = HttpUtil.getHttp(imgFile, request, "/upload/articleImg/");
            hashMap.put("error",0);
            hashMap.put("url",http);
        }catch (Exception e){
            hashMap.put("error",1);
            e.printStackTrace();
        }
        return hashMap;
    }

    @RequestMapping("/showAllImg")
    public Map showAllImg(HttpServletRequest request,HttpSession session){
        HashMap hashMap = new HashMap();
        String s = request.getContextPath() + "/upload/articleImg/";
        hashMap.put("current_url",s);
        String realPath = session.getServletContext().getRealPath("/upload/articleImg/");
        File file = new File(realPath);
        File[] files = file.listFiles();
        hashMap.put("total_count",files.length);
        ArrayList arrayList = new ArrayList();
        for (File file1 : files) {
            HashMap fileMap = new HashMap();
            fileMap.put("is_dir",false);
            fileMap.put("has_file",false);
            fileMap.put("filesize",file1.length());
            fileMap.put("is_photo",true);
            String name = file1.getName();
            String extension = FilenameUtils.getExtension(name);
            fileMap.put("filetype",extension);
            fileMap.put("filename",name);
            String time = name.split("_")[0];
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            String format = simpleDateFormat.format(new Date(Long.valueOf(time)));
            fileMap.put("datatime",format);
            arrayList.add(fileMap);
        }
        hashMap.put("file_list",arrayList);
        return hashMap;
    }

    @RequestMapping("/addArticle")
    public String addArticle(Article article,MultipartFile inputfile,HttpServletRequest request){
        if (article.getId() == null || "".equals(article.getId())){
            // 添加
            String http = HttpUtil.getHttp(inputfile, request, "/upload/articleImg/");
            article.setImg(http);
            articleService.add(article);
        }else{
            // 修改
            articleService.modify(article);
        }
        return "ok";
    }


    @RequestMapping("/queryByPage")
    public Map queryByPage(Integer page,Integer rows){
        HashMap hashMap = new HashMap();
        List<Article> articles = articleService.queryByPage(null, page, rows);
        Integer records = articleService.queryCount(null);
        Integer total = records % rows == 0 ? records / rows : records / rows + 1;
        hashMap.put("page",page);
        hashMap.put("rows",articles);
        hashMap.put("total",total);
        hashMap.put("records",records);
        return hashMap;
    }

    @RequestMapping("/editArticle")
    public Map editArticle(String oper,Article article,String[] id){
        HashMap hashMap = new HashMap();
        if(oper.equals("add")){
            String articleId = UUID.randomUUID().toString();
            article.setId(articleId);
            articleService.add(article);
            hashMap.put("articleId",articleId);
        }else if(oper.equals("edit")){
            articleService.modify(article);
            hashMap.put("articleId", article.getId());
        }else{
            articleService.remove(id);
        }
        return hashMap;
    }

    @RequestMapping("/removeArticle")
    public void removeAarticle(String[] id){
        articleService.remove(id);
    }

    @RequestMapping("/uploadArticle")
    public Map uploadArticle(MultipartFile img, String articleId, HttpSession session, HttpServletRequest request) {
        HashMap hashMap = new HashMap();
        //获取真是路径
        String realPath = session.getServletContext().getRealPath("/upload/articleImg/");
        //判断文件夹是否存在
        File file = new File(realPath);
        if (!file.exists()) {
            file.mkdirs();
        }
        String uri = HttpUtil.getHttp(img, request, "/upload/articleImg/");
        Article article = new Article();
        article.setId(articleId);
        article.setImg(uri);
        articleService.modify(article);
        hashMap.put("status", 200);
        return hashMap;
    }


    //6.文章详情接口
    @RequestMapping("/articleMsg")
    public Map articleMsg(String uid,String id){
        HashMap hashMap = new HashMap();
        Article article = articleService.queryOne(id);
        hashMap.put("status","200");
        hashMap.put("article",article);
        return hashMap;

    }
}

