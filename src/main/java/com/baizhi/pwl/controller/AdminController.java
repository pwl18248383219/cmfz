package com.baizhi.pwl.controller;


import com.baizhi.pwl.service.AdminService;
import com.baizhi.pwl.util.CreateValidateCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    AdminService adminService;

    //登陆
    @RequestMapping("/login")
    @ResponseBody
    public Map login(String clientCode,String username,String password){
        System.out.println(clientCode);
        Map map = adminService.login(clientCode, username, password);
        return map;
    }

    //退出
    @RequestMapping("/logout")
    public String logout(HttpSession session){
        session.removeAttribute("admin");
        return "redirect:/jsp/login.jsp";
    }

    @RequestMapping("/createImg")
    @ResponseBody
    public void createImg(HttpSession session, HttpServletResponse response) throws IOException {
        CreateValidateCode vcode = new CreateValidateCode();
        //获取随机验证码
        String code = vcode.getCode();
        //把图片输出到client
        vcode.write(response.getOutputStream());
        //把验证码存到session中
        session.setAttribute("serverCode",code);
    }
}


