package com.baizhi.pwl.service;

import com.baizhi.pwl.dao.CounterDao;
import com.baizhi.pwl.entity.Counter;
import org.apache.poi.ss.formula.functions.Count;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class CounterServiceImpl implements CounterService {
    @Autowired
    CounterDao counterDao;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public List<Counter> queryByUid(String uid,String cid) {
        Counter counter = new Counter();
        counter.setUserId(uid);
        counter.setCourseId(cid);
        List<Counter> counters = counterDao.select(counter);
        return counters;
    }

    @Override
    public void add(String uid, String title, String cid) {
        Counter counter = new Counter();
        counter.setUserId(uid);
        counter.setCourseId(cid);
        counter.setTitle(title);
        String s = UUID.randomUUID().toString();
        counter.setId(s);
        counter.setCount(0);
        counter.setCreateDate(new Date());
        counterDao.insert(counter);
    }

    @Override
    public void remove(String id) {
        Counter counter = new Counter();
        counter.setId(id);
        counterDao.delete(counter);
    }
}
