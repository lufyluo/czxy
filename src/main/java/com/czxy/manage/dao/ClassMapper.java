package com.czxy.manage.dao;

import com.czxy.manage.infrastructure.response.BaseResponse;
import com.czxy.manage.model.PageParam;
import com.czxy.manage.model.entity.ClassEntity;
import com.czxy.manage.model.entity.ClassInformationEntity;
import com.czxy.manage.model.entity.ClassOrgEntity;
import com.czxy.manage.model.entity.ClassStudentEntity;
import com.czxy.manage.model.vo.classes.ClassInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ClassMapper {
    List<ClassOrgEntity> queryAll(String param);

    Integer delete(@Param("id") List<Integer> id);

    ClassInformationEntity query(Integer id);

    List<ClassStudentEntity> queryAllStudent(Integer classId);

    Integer insert(ClassEntity classEntity);

    Integer update(ClassEntity classEntity);
}
