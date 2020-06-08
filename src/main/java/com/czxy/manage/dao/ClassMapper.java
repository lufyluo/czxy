package com.czxy.manage.dao;

import com.czxy.manage.infrastructure.response.BaseResponse;
import com.czxy.manage.model.PageParam;
import com.czxy.manage.model.entity.*;
import com.czxy.manage.model.vo.classes.ClassInfo;
import com.czxy.manage.model.vo.classes.ClassPageParam;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ClassMapper {
    List<ClassOrgEntity> queryAll(ClassPageParam param);

    Integer delete(@Param("id") List<Integer> id);

    ClassInformationEntity query(Integer id);

    List<ClassStudentEntity> queryAllStudent(Integer classId);

    Integer insert(ClassEntity classEntity);

    Integer update(ClassEntity classEntity);

    Integer queryByName(String className);

    ClassEntity queryClass(Integer classId);

    Integer queryRecentByStudentUserId(Integer userId);

    Integer clearStudent(Integer classId);

    List<CountEntity> queryCount(@Param("collect") List<Integer> collect);
}
