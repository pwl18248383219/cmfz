package com.baizhi.pwl.util;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;

public class HttpUtil {
    public static String getHttp(MultipartFile file, HttpServletRequest request, String path) {
        String realPath = request.getSession().getServletContext().getRealPath(path);
        //防止文件重名
        String name = new Date().getTime() + "_" + file.getOriginalFilename();
        try {
            file.transferTo(new File(realPath, name));
        } catch (IOException e) {
            e.printStackTrace();
        }
        //获取HTTP协议
        String scheme = request.getScheme();
        //获取ip
        String localhost = null;
        try {
            localhost = InetAddress.getLocalHost().toString();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        //获取端口号
        int serverPort = request.getServerPort();
        //获取项目名
        String contextPath = request.getContextPath();
        String uri = scheme + "://" + localhost.split("/")[1] + ":" + serverPort + contextPath + path + name;

        return uri;
    }
}
