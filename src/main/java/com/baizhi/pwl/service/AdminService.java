package com.baizhi.pwl.service;

import com.baizhi.pwl.entity.Admin;

import java.util.Map;

public interface AdminService {

    //登陆
    public Map login(String clientCode,String username,String password);
}
