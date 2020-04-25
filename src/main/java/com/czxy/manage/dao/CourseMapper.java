package com.czxy.manage.dao;

import com.czxy.manage.model.entity.CourseDetailEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CourseMapper {
    List<CourseDetailEntity> get(Integer arrangeId);
}
