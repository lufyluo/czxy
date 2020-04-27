package com.czxy.manage.dao;

import com.czxy.manage.model.entity.StudentDetailEntity;
import com.czxy.manage.model.vo.student.StudentPageParam;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface StudentMapper {
    List<StudentDetailEntity> query(StudentPageParam<String> pageParam);

    Boolean sign(@Param("studentIds") List<Integer> studentIds);
}
