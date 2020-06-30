package com.czxy.manage.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IndexMapper {

    List<String> getUrl();

    String queryNewMessage();

    Integer getStudentNumbers();

    Integer getTeacherNumbers();

    Integer getSiteNumbers();

    Integer getSubjectNumbers();
}
