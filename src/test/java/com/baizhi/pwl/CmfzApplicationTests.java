package com.baizhi.pwl;


import com.baizhi.pwl.dao.AdminDao;
import com.baizhi.pwl.entity.Admin;

import io.goeasy.GoEasy;
import org.junit.Test;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CmfzApplicationTests {

    @Autowired
    AdminDao adminDao;

    @Test
    public void testDao(){
        List<Admin> admins = adminDao.selectAll();
        for (Admin admin : admins) {
            System.out.println(admin);
        }
    }

    @Test
    public void testgoeasy(){
        GoEasy goEasy = new GoEasy( "http://rest-hangzhou.goeasy.io", "BC-c54978d75ba841fa89f22df0f70bdeb8");
        goEasy.publish("qqq", "Hello, GoEasy!");
    }
}
