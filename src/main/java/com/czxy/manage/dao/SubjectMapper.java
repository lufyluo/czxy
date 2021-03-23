package com.czxy.manage.dao;

import com.czxy.manage.model.entity.CourseDetailEntity;
import com.czxy.manage.model.entity.SubjectDetailEntity;
import com.czxy.manage.model.entity.SubjectEntity;
import com.czxy.manage.model.vo.subject.SubjectDetailInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SubjectMapper {

    List<SubjectDetailEntity> query(@Param("param") String param, @Param("typeId") Integer typeId, @Param("category") Integer category);

    Integer add(SubjectEntity subjectEntity);

    SubjectEntity queryById(@Param("subjectId") Integer subjectId);

    Integer update(SubjectEntity subjectEntity);

    Integer delete(@Param("subjectIds") List<Integer> subjectIds);

    List<CourseDetailEntity> queryByArrangeId(@Param("arrangeId") Integer arrangeId);

    List<SubjectEntity> getByTeacherId(@Param("teacherId") Integer teacherId);

    Integer batchInsert(List<SubjectEntity> subjectEntities);
}
