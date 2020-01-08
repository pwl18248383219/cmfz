package com.baizhi.pwl.service;

import com.baizhi.pwl.dao.AlbumDao;
import com.baizhi.pwl.entity.Album;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class AlbumServiceImpl implements AlbumService {

    @Autowired
    AlbumDao albumDao;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public Integer queryCount(Album album) {
        int count = albumDao.selectCount(album);
        return count;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public List<Album> queryByPage(Album album,Integer page,Integer rows) {
        List<Album> albums = albumDao.selectByRowBounds(album, new RowBounds((page - 1) * rows, rows));
        return albums;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public Album queryOne(String id) {
        Album album = new Album();
        album.setId(id);
        Album album1 = albumDao.selectOne(album);
        return album1;
    }

    @Override
    public void add(Album album) {
        albumDao.insert(album);
    }

    @Override
    public void modify(Album album) {
        albumDao.updateByPrimaryKeySelective(album);
    }

    @Override
    public void remove(String[] id) {
        albumDao.deleteByIdList(Arrays.asList(id));
    }
}
