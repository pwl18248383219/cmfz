package com.baizhi.pwl.service;

import com.baizhi.pwl.dao.ChapterDao;
import com.baizhi.pwl.entity.Chapter;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Service
@Transactional
public class ChapterServiceImpl implements ChapterService {

    @Autowired
    ChapterDao chapterDao;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public List<Chapter> queryByPage(Integer page, Integer rows, String albumId) {
        Chapter chapter = new Chapter();
        chapter.setAlbumId(albumId);
        List<Chapter> chapters = chapterDao.selectByRowBounds(chapter, new RowBounds((page - 1) * rows, rows));
        return chapters;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public Integer queryCount(String albumId) {
        Chapter chapter = new Chapter();
        chapter.setAlbumId(albumId);
        int count = chapterDao.selectCount(chapter);
        return count;
    }

    @Override
    public List<Chapter> queryByAlbumId(String albumId) {
        Chapter chapter = new Chapter();
        chapter.setId(albumId);
        List<Chapter> chapters = chapterDao.select(chapter);
        return chapters;
    }

    @Override
    public void add(Chapter chapter) {
        chapterDao.insert(chapter);
    }

    @Override
    public void modify(Chapter chapter) {
        chapterDao.updateByPrimaryKeySelective(chapter);
    }

    @Override
    public void remove(String[] id) {
        chapterDao.deleteByIdList(Arrays.asList(id));
    }
}
