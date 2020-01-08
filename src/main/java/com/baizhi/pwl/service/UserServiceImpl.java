package com.baizhi.pwl.service;

import com.baizhi.pwl.dao.UserDao;
import com.baizhi.pwl.entity.Admin;
import com.baizhi.pwl.entity.MapDto;
import com.baizhi.pwl.entity.User;
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
public class UserServiceImpl implements UserService {

    @Autowired
    UserDao userDao;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public List<User> queryByPage(User user, Integer page, Integer rows) {
        RowBounds rowBounds = new RowBounds((page-1)*rows,rows);
        List<User> users = userDao.selectByRowBounds(user, rowBounds);
        return users;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public Integer queryCount() {
        int count = userDao.selectCount(null);
        return count;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public User queryOne(User user) {
        User user1 = userDao.selectOne(user);
        return user1;
    }

    @Override
    public void add(User user) {

        userDao.insert(user);
    }

    @Override
    public Map modify(User user) {
        HashMap hashMap = new HashMap();
        try {
            userDao.updateByPrimaryKeySelective(user);
            User user1 = new User();
            user1.setId(user.getId());
            User user2 = userDao.selectOne(user);
            hashMap.put("status","200");
            hashMap.put("user",user2);
        }catch (Exception e){
            e.printStackTrace();
            hashMap.put("message","修改失败");
        }
        return hashMap;
    }

    @Override
    public void remove(String[] id) {
        userDao.deleteByIdList(Arrays.asList(id));
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public Integer queryUserByTime(String sex, Integer day) {
        Integer integer = userDao.selectUserByTime(sex, day);
        return integer;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public List<MapDto> queryBySexGetLocation(String sex) {
        List<MapDto> mapDtos = userDao.queryBySexGetLocation(sex);
        return mapDtos;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public Map login(String phone,String password) {
        HashMap hashMap = new HashMap();
        User user = new User();
        user.setPhone(phone);
        User user1 = userDao.selectOne(user);
        if(user1 == null){
            hashMap.put("status","-200");
            hashMap.put("message","该用户不存在");
        }else if(!user1.getPassword().equals(password)){
            hashMap.put("status","-200");
            hashMap.put("message","密码不正确");
        }else{
            hashMap.put("status","200");
            hashMap.put("user",user1);
        }
        return hashMap;
    }



}
