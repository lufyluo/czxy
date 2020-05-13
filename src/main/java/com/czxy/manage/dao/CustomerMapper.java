package com.czxy.manage.dao;

import com.czxy.manage.model.entity.CustomerEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CustomerMapper {
    List<CustomerEntity> query(String param,Integer star);
}
