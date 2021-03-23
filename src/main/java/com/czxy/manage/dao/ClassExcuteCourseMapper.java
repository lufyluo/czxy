package com.czxy.manage.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ClassExcuteCourseMapper {
    Integer copySnapshot(@Param("arrangeId") Integer arrangeId);
}
