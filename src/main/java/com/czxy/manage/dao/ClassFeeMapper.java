package com.czxy.manage.dao;

import com.czxy.manage.model.entity.ClassFeeEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
public interface ClassFeeMapper {
    List<ClassFeeEntity> query(@Param("classId") Integer classId);
}
