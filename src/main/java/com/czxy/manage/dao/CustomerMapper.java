package com.czxy.manage.dao;

import com.czxy.manage.model.entity.CustomerEntity;
import com.czxy.manage.model.vo.customer.CustomerInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CustomerMapper {
    List<CustomerEntity> query(@Param("param") String param, @Param("star") Integer star);

    Integer update(CustomerInfo customerInfo);

    void insert(CustomerEntity customerEntity);

    Integer delete(List<Integer> ids);

    Integer updateAll(CustomerEntity customerEntity);

    Integer countContactor(@Param("orgId") Integer orgId);
}
