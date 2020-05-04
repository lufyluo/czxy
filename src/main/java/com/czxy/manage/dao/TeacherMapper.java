package com.czxy.manage.dao;

import com.czxy.manage.model.entity.TeacherDetailEntity;
import com.czxy.manage.model.entity.TeacherEntity;
import com.czxy.manage.model.vo.teacher.TeacherPageParam;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TeacherMapper {
    List<TeacherDetailEntity> query(TeacherPageParam<String> pageParam);

    Integer insert(TeacherEntity teacherEntity);

    Integer delete(@Param("teacherIds") List<Integer> teacherIds);

    Integer update(TeacherEntity teacherEntity);

    Integer queryUserId(TeacherEntity teacherEntity);
}
