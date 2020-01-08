package com.baizhi.pwl.service;

import com.baizhi.pwl.dao.CourseDao;
import com.baizhi.pwl.entity.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class CourseServiceImpl implements CourseService {

    @Autowired
    CourseDao courseDao;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public List<Course> queryById(String id) {
        Course course = new Course();
        course.setUserId(id);
        List<Course> courses = courseDao.select(course);
        return courses;
    }

    @Override
    public void add(Course course) {
        String s = UUID.randomUUID().toString();
        course.setId(s);
        course.setCreateDate(new Date());
        courseDao.insert(course);
    }

    @Override
    public void remove(String id) {
        Course course = new Course();
        course.setId(id);
        courseDao.delete(course);
    }
}
