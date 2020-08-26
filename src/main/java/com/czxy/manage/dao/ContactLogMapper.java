package com.czxy.manage.dao;

import com.czxy.manage.model.entity.ContactLogEntity;
import com.czxy.manage.model.vo.customerContacts.ContactLogInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ContactLogMapper {
    Integer insert(ContactLogInfo contactLogInfo);

    List<ContactLogEntity> query(Integer planId);

    Integer delete(Integer id);

    Integer update(ContactLogEntity entity);
}
