package com.czxy.manage.dao;

import com.czxy.manage.model.entity.ClassFeeEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ClassFeeMapper {
    List<ClassFeeEntity> query(Integer classId);
}
