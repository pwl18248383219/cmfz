package com.baizhi.pwl.controller;

import com.alibaba.excel.EasyExcel;
import com.baizhi.pwl.entity.Banner;
import com.baizhi.pwl.entity.BannerDataListener;
import com.baizhi.pwl.service.BannerService;
import com.baizhi.pwl.util.HttpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;

@RestController
@RequestMapping("/banner")
public class BannerController {

    @Autowired
    BannerService bannerService;

    @RequestMapping("/queryByPage")
    public Map queryByPage(int page, int rows) {
        HashMap hashMap = new HashMap();
        //查询总条数
        Integer records = bannerService.queryCount();
        //计算总页数
        Integer total = records % rows == 0 ? records / rows : records / rows + 1;
        List<Banner> banners = bannerService.queryByPage(null, page, rows);
        hashMap.put("records", records);
        hashMap.put("total", total);
        hashMap.put("page", page);
        hashMap.put("rows", banners);
        return hashMap;
    }


    @RequestMapping("/uploadBanner")
    public Map uploadBanner(MultipartFile url, String bannerId, HttpSession session, HttpServletRequest request) {
        HashMap hashMap = new HashMap();
        //获取真是路径
        String realPath = session.getServletContext().getRealPath("/upload/img/");
        //判断文件夹是否存在
        File file = new File(realPath);
        if (!file.exists()) {
            file.mkdirs();
        }
        String uri = HttpUtil.getHttp(url, request, "/upload/img/");
        Banner banner = new Banner();
        banner.setId(bannerId);
        banner.setUrl(uri);
        bannerService.modify(banner);
        hashMap.put("status", 200);
        return hashMap;
    }

    @RequestMapping("/editBanner")
    public Map editBanner(String oper, Banner banner, String[] id) {
        HashMap hashMap = new HashMap();

        //添加
        if (oper.equals("add")) {
            String uid = UUID.randomUUID().toString();
            banner.setId(uid);
            bannerService.add(banner);
            hashMap.put("bannerId", uid);
        } else if (oper.equals("edit")) {//修改

           /* System.out.println("++++++++++++++" + banner.getUrl() == "");
            System.out.println("************" + banner.getUrl().equals(""));
            System.out.println("------------" + banner);
            System.out.println(url);
            if (banner.getUrl().equals("")) {
                banner.setUrl(null);    
            } else {
                String uri = HttpUtil.getHttp(url, request, "/upload/img");
            }*/
            bannerService.modify(banner);
            hashMap.put("bannerId", banner.getId());
        } else {//删除
            bannerService.remove(id);

        }
        return hashMap;
    }

    //EasyExcel导出轮播图信息
    @RequestMapping("/exportBanner")
    public void exportBanner(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<Banner> banners = bannerService.queryAll();
        for (Banner banner : banners) {
            String[] split = banner.getUrl().split("/");
            String url = split[split.length-1];
            banner.setUrl(url);
        }
        response.setHeader("Content-Disposition", "attachment; filename=" + new Date().getTime()+".xlsx");
        ServletOutputStream outputStream = response.getOutputStream();
        EasyExcel.write(outputStream,Banner.class).sheet("banner").doWrite(banners);
    }

//    EasyExcel导入轮播图信息
    @RequestMapping("/importBanner")
    public Map importBanner(MultipartFile inputBanner,HttpServletRequest request,HttpSession session){
        //获取真是路径
        String realPath = session.getServletContext().getRealPath("/upload/importBanner/");
        //判断文件夹是否存在
        File file = new File(realPath);
        if (!file.exists()) {
            file.mkdirs();
        }
        String http = HttpUtil.getHttp(inputBanner, request, "/upload/importBanner/");
        String s = http.split("/")[http.split("/").length - 1];
        String path = realPath+s;
        EasyExcel.read(path,Banner.class,new BannerDataListener()).sheet().doRead();
        HashMap hashMap = new HashMap();
        hashMap.put("status",200);
        return hashMap;
    }
    @RequestMapping("downloadBanner")
    public void downloadBanner(HttpServletResponse response) throws IOException {
        Banner banner = new Banner();
        response.setHeader("Content-Disposition", "attachment; filename=" + new Date().getTime()+".xlsx");
        ServletOutputStream outputStream = response.getOutputStream();
        EasyExcel.write(outputStream,Banner.class).sheet("banner").doWrite(Arrays.asList(banner));
    }

}
