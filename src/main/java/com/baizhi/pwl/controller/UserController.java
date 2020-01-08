package com.baizhi.pwl.controller;

import com.alibaba.druid.support.json.JSONUtils;
import com.baizhi.pwl.entity.Article;
import com.baizhi.pwl.entity.User;
import com.baizhi.pwl.service.UserService;
import com.baizhi.pwl.util.HttpUtil;
import com.baizhi.pwl.util.SmsUtil;
import io.goeasy.GoEasy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.*;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    @RequestMapping("/editUser")
    public Map editUser(String oper, User user, String[] id){
        HashMap hashMap = new HashMap();
        if(oper.equals("add")){
            String articleId = UUID.randomUUID().toString();
            user.setId(articleId);
            userService.add(user);
            hashMap.put("articleId",articleId);
        }else if(oper.equals("edit")){
            userService.modify(user);
            hashMap.put("articleId", user.getId());
        }else{
            userService.remove(id);
        }
        return hashMap;
    }


    @RequestMapping("/queryByPage")
    public Map queryByPage(Integer page,Integer rows){
        HashMap hashMap = new HashMap();
        List<User> users = userService.queryByPage(null, page, rows);
        Integer records = userService.queryCount();
        Integer total = records % rows == 0 ? records / rows : records / rows + 1;
        hashMap.put("page",page);
        hashMap.put("rows",users);
        hashMap.put("total",total);
        hashMap.put("records",records);
        return hashMap;
    }

    @RequestMapping("/showUserTime")
    public Map showUserTime(){
        HashMap hashMap = new HashMap();
        ArrayList manList = new ArrayList();
        manList.add(userService.queryUserByTime("0",1));
        manList.add(userService.queryUserByTime("0",7));
        manList.add(userService.queryUserByTime("0",30));
        manList.add(userService.queryUserByTime("0",365));
        ArrayList womenList = new ArrayList();
        womenList.add(userService.queryUserByTime("1",1));
        womenList.add(userService.queryUserByTime("1",7));
        womenList.add(userService.queryUserByTime("1",30));
        womenList.add(userService.queryUserByTime("1",365));
        hashMap.put("man",manList);
        hashMap.put("women",womenList);
        return hashMap;
    }

    @RequestMapping("addUser")
    public void addUser(){
        User user = new User();
        user.setId(UUID.randomUUID().toString());
        user.setSex("0");
        user.setLocation("河南");
        user.setRegisterDate(new Date());
        userService.add(user);
        GoEasy goEasy = new GoEasy( "http://rest-hangzhou.goeasy.io", "BC-2cee62d1b57b4c5c9d23032665b66aef");
        Map map = showUserTime();
        String s = JSONUtils.toJSONString(map);
        System.out.println(s);
        goEasy.publish("cmfz", s);
    }

    @RequestMapping("/queryBySexGetLocation")
    public Map queryBySexGetLocation(){
        HashMap hashMap = new HashMap();
        List manList = userService.queryBySexGetLocation("0");
        List womenList = userService.queryBySexGetLocation("1");
        hashMap.put("man",manList);
        hashMap.put("women",womenList);
        return hashMap;
    }



    //1.登陆接口
    @RequestMapping("/login")
    public Map login(String phone,String password){
        Map hashmap = userService.login(phone, password);
        return hashmap;
    }
    //2.发送验证码
    @RequestMapping("/sendCode")
    public Map sendCode(String phone){
        String s = UUID.randomUUID().toString();
        String code = s.substring(0, 3);
        SmsUtil.send(phone,code);
        // 将验证码保存值Redis  Key phone_186XXXX Value code 1分钟有效
        HashMap hashMap = new HashMap();
        hashMap.put("status","200");
        hashMap.put("message","短信发送成功");
        return hashMap;
    }
    //3.注册接口
//    @RequestMapping("/register")
//    public Map register(String code){
//
//        return null;
//    }
    //4.补充个人信息接口
    @RequestMapping("/moifyUser")
    public Map moifyUser(User user){
        Map hashMap = userService.modify(user);
        return hashMap;
    }
    //

}
