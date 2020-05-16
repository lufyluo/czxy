package com.czxy.manage.dao;

import com.czxy.manage.model.entity.CustomerEntity;
import com.czxy.manage.model.vo.customer.CustomerInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CustomerMapper {
    List<CustomerEntity> query(String param, Integer star);

    Integer update(CustomerInfo customerInfo);

    void insert(CustomerEntity customerEntity);
}
