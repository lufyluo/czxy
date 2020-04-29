package com.czxy.manage.dao;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ClassExcuteCourseMapper {
    Integer copySnapshot(Integer arrangeId);
}
