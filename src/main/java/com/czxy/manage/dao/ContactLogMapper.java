package com.czxy.manage.dao;

import com.czxy.manage.model.entity.ContactLogEntity;
import com.czxy.manage.model.vo.customerContacts.ContactLogInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ContactLogMapper {
    Integer insert(ContactLogInfo contactLogInfo);

    List<ContactLogEntity> query(@Param("planId") Integer planId);

    Integer delete(@Param("id") Integer id);

    Integer update(ContactLogEntity entity);
}
