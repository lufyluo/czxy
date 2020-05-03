package com.czxy.manage.dao;

import com.czxy.manage.model.entity.TeacherEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TeacherMapper {

    Integer insert(TeacherEntity teacherEntity);
}
