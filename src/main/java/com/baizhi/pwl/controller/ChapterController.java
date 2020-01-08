package com.baizhi.pwl.controller;


import com.baizhi.pwl.entity.Chapter;
import com.baizhi.pwl.service.ChapterService;
import com.baizhi.pwl.util.HttpUtil;
import org.apache.commons.io.FileUtils;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.audio.mp3.MP3AudioHeader;
import org.jaudiotagger.tag.TagException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/chapter")
public class ChapterController {
    @Autowired
    ChapterService chapterService;

    @RequestMapping("/queryByPage")
    public Map queryByPage(Integer page, Integer rows, String albumId) {
        HashMap hashMap = new HashMap();
        //分页查询到的数据
        List<Chapter> chapters = chapterService.queryByPage(page, rows, albumId);
        //查询总行数
        Integer records = chapterService.queryCount(albumId);
        //计算总页数
        Integer total = records % rows == 0 ? records / rows : records / rows + 1;
        hashMap.put("page", page);
        hashMap.put("rows", chapters);
        hashMap.put("total", total);
        hashMap.put("records", records);
        return hashMap;
    }

    @RequestMapping("/uploadChapter")
    public Map uploadChapter(MultipartFile url, String chapterId, HttpSession session, HttpServletRequest request) throws TagException, ReadOnlyFileException, CannotReadException, InvalidAudioFrameException, IOException {
        HashMap hashMap = new HashMap();
        //获取真是路径
        String realPath = session.getServletContext().getRealPath("/upload/music/");
        //判断文件夹是否存在
        File file = new File(realPath);
        if (!file.exists()) {
            file.mkdirs();
        }
        String uri = HttpUtil.getHttp(url, request, "/upload/music/");
        Chapter chapter = new Chapter();
        chapter.setUrl(uri);
        chapter.setId(chapterId);
        //获取文件大小
        Double size = Double.valueOf(url.getSize() / 1024 / 1024);
        chapter.setSize(size);
        //获取文件名
        String[] split = uri.split("/");
        String name = split[split.length - 1];
        // 通过文件获取AudioFile对象 音频解析对象
        AudioFile read = AudioFileIO.read(new File(realPath, name));
        // 通过音频解析对象 获取 头部信息 为了信息更准确 需要将AudioHeader转换为MP3AudioHeader
        MP3AudioHeader audioHeader = (MP3AudioHeader) read.getAudioHeader();
        // 获取音频时长 秒
        int trackLength = audioHeader.getTrackLength();
        String time = trackLength / 60 + "分" + trackLength % 60 + "秒";
        chapter.setTime(time);
        chapterService.modify(chapter);
        hashMap.put("status", 200);
        return hashMap;
    }

    @RequestMapping("/editChapter")
    public Map editChapter(String oper, Chapter chapter, String[] id, String albumId) {
        HashMap hashMap = new HashMap();

        //添加
        if (oper.equals("add")) {
            String uid = UUID.randomUUID().toString();
            chapter.setId(uid);
            chapter.setAlbumId(albumId);
            chapterService.add(chapter);
            hashMap.put("chapterId", uid);
        } else if (oper.equals("edit")) {//修改
            chapterService.modify(chapter);
            hashMap.put("chapterId", chapter.getId());
        } else {//删除
            chapterService.remove(id);

        }
        return hashMap;
    }

    @RequestMapping("downloadChapter")
    public void downloadChapter(String url, HttpServletResponse response, HttpSession session) throws IOException {
        // 处理url路径 找到文件
        String[] split = url.split("/");
        String realPath = session.getServletContext().getRealPath("/upload/music/");
        String name = split[split.length - 1];
        File file = new File(realPath, name);
        // 通过url获取本地文件
        response.setHeader("Content-Disposition", "attachment; filename=" + name);
        ServletOutputStream outputStream = response.getOutputStream();
        FileUtils.copyFile(file, outputStream);
    }
}
