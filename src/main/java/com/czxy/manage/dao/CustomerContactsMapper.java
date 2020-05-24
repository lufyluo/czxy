package com.czxy.manage.dao;

import com.czxy.manage.model.PageParam;
import com.czxy.manage.model.entity.CustomerContactsEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CustomerContactsMapper {
    List<CustomerContactsEntity> query(PageParam<String> pageParam);
}
