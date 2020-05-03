package com.czxy.manage.dao;

import com.czxy.manage.model.entity.TeacherDetailEntity;
import com.czxy.manage.model.vo.teacher.TeacherPageParam;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TeacherMapper {
    List<TeacherDetailEntity> query(TeacherPageParam<String> pageParam);
}
