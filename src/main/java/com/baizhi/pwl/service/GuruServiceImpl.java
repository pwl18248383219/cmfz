package com.baizhi.pwl.service;


import com.baizhi.pwl.dao.GuruDao;
import com.baizhi.pwl.entity.Guru;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Service
@Transactional
public class GuruServiceImpl implements GuruService {

    @Autowired
    GuruDao guruDao;

    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    @Override
    public List<Guru> queryAll() {
        List<Guru> gurus = guruDao.selectAll();
        return gurus;
    }

    @Override
    public void add(Guru guru) {
        guruDao.insert(guru);
    }

    @Override
    public void modify(Guru guru) {
        guruDao.updateByPrimaryKeySelective(guru);
    }


    @Override
    public void remove(String[] id) {
        guruDao.deleteByIdList(Arrays.asList(id));
    }
}
