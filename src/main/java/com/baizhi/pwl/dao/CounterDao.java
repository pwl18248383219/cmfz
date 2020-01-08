package com.baizhi.pwl.dao;

import com.baizhi.pwl.entity.Counter;
import tk.mybatis.mapper.additional.idlist.DeleteByIdListMapper;
import tk.mybatis.mapper.common.Mapper;

public interface CounterDao  extends Mapper<Counter>, DeleteByIdListMapper<Counter,String> {
}
