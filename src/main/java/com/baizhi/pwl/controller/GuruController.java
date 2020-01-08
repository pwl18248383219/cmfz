package com.baizhi.pwl.controller;


import com.baizhi.pwl.entity.Guru;
import com.baizhi.pwl.service.GuruService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/guru")
public class GuruController {
    @Autowired
    GuruService guruService;

    @RequestMapping("/queryAllGuru")
    public List<Guru> queryAllGuru(){
        List<Guru> gurus = guruService.queryAll();
        return gurus;
    }
}
