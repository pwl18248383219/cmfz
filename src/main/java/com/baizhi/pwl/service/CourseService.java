package com.baizhi.pwl.service;


import com.baizhi.pwl.entity.Course;

import java.util.List;

public interface CourseService {

    //根据条件查一个
    public List<Course> queryById(String id);
    //添加
    public void add(Course course);
    //删除
    public void remove(String id);
}
