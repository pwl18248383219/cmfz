package com.baizhi.pwl.controller;

import com.baizhi.pwl.entity.Counter;
import com.baizhi.pwl.service.CounterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/counter")
public class CounterController {
    @Autowired
    CounterService counterService;

    //11. 展示计数器
    @RequestMapping("/queryByUid")
    public Map queryByUid(String uid, String cid){
        List<Counter> counters = counterService.queryByUid(uid, cid);
        HashMap hashMap = new HashMap();
        hashMap.put("atatus","200");
        hashMap.put("counters",counters);
        return hashMap;
    }
    //12.添加计数器
    @RequestMapping("/addCounter")
    public Map addCounter(String uid,String title,String cid){
        counterService.add(uid,title,cid);
        List<Counter> counters = counterService.queryByUid(uid, cid);
        HashMap hashMap = new HashMap();
        hashMap.put("atatus","200");
        hashMap.put("counters",counters);
        return hashMap;
    }
    //13.删除计数器
    @RequestMapping("/renmoveCounter")
    public Map renmoveCounter(String id,String uid,String cid){
        counterService.remove(id);
        List<Counter> counters = counterService.queryByUid(uid, cid);
        HashMap hashMap = new HashMap();
        hashMap.put("atatus","200");
        hashMap.put("counters",counters);
        return hashMap;
    }
}
