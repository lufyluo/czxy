package com.czxy.manage.dao;

import com.czxy.manage.model.entity.TeacherDetailEntity;
import com.czxy.manage.model.entity.TeacherEntity;
import com.czxy.manage.model.entity.TeacherInformationEntity;
import com.czxy.manage.model.vo.teacher.TeacherInformationInfo;
import com.czxy.manage.model.vo.teacher.TeacherPageParam;
import com.czxy.manage.model.vo.teacher.TeacherWechatInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TeacherMapper {
    List<TeacherDetailEntity> query(TeacherPageParam<String> pageParam);

    Integer insert(TeacherEntity teacherEntity);

    Integer delete(@Param("teacherIds") List<Integer> teacherIds);

    Integer deleteByUserIds(List<Integer> userIds);

    Integer update(TeacherEntity teacherEntity);

    Integer queryUserId(TeacherEntity teacherEntity);

    TeacherInformationEntity queryAll(@Param("teacherId") Integer teacherId);

    String queryName(@Param("teacherId") Integer teacherId);

    List<TeacherWechatInfo> get();

    Integer batchInsert(List<TeacherEntity> teacherEntities);


    TeacherEntity queryByUserId(Integer userId);

    List<TeacherEntity> queryByUserIds(List<Integer> userIds);

    List<TeacherDetailEntity> queryByNames(List<String> collect);
}
