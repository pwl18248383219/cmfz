package com.baizhi.pwl.service;

import com.baizhi.pwl.dao.AdminDao;
import com.baizhi.pwl.entity.Admin;
import org.apache.http.HttpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Component
public class AdminServiceImpl implements AdminService {

    @Autowired
    AdminDao adminDao;
    @Autowired
    HttpSession session;

    @Override
    public Map login(String clientCode, String username, String password) {
        HashMap map = new HashMap();
        String serverCode = session.getAttribute("serverCode").toString();
        if(!serverCode.equals(clientCode)){
            map.put("msg","验证码错误");
            return map;
        }else{
            Admin admin = new Admin();
            admin.setUsername(username);
            Admin admin1 = adminDao.selectOne(admin);
            if(admin1 == null){
                map.put("status",400);
                map.put("msg","该用户不存在");
            }else if(!admin1.getPassword().equals(password)){
                map.put("status",400);
                map.put("msg","密码不正确");
            }else{
                map.put("status",200);
                session.setAttribute("admin",admin1);
            }
            return map;
        }

    }
}
