package com.czxy.manage.dao;

import com.czxy.manage.model.entity.ClassEntity;
import com.czxy.manage.model.entity.ClassOrgEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ClassMapper {
    List<ClassOrgEntity> queryAll(String param);
}
