package com.baizhi.pwl.controller;

import com.baizhi.pwl.entity.Course;
import com.baizhi.pwl.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/course")
@RestController
public class CourseController {
    @Autowired
    CourseService courseService;
    //8.展示功课
    @RequestMapping("/queryByUid")
    public Map queryByUid(String uid){
        List<Course> courses = courseService.queryById(uid);
        HashMap hashMap = new HashMap();
        hashMap.put("status","200");
        hashMap.put("option",courses);
        return hashMap;
    }
    //9.添加功课
    @RequestMapping("/addCourse")
    public Map addCourse(String uid,String title){
        Course course = new Course();
        course.setUserId(uid);
        course.setTitle(title);
        courseService.add(course);
        List<Course> courses = courseService.queryById(uid);
        HashMap hashMap = new HashMap();
        hashMap.put("status","200");
        hashMap.put("option",courses);
        return hashMap;
    }
    //10.删除功课
    @RequestMapping("/removeCourse")
    public Map removeCourse(String uid,String id){
        courseService.remove(id);
        List<Course> courses = courseService.queryById(uid);
        HashMap hashMap = new HashMap();
        hashMap.put("status","200");
        hashMap.put("option",courses);
        return hashMap;
    }
}
