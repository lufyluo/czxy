package com.czxy.manage.dao;

import com.czxy.manage.model.entity.StudentAddEntity;
import com.czxy.manage.model.entity.StudentDetailEntity;
import com.czxy.manage.model.entity.StudentEntity;
import com.czxy.manage.model.entity.StudentUpdateEntity;
import com.czxy.manage.model.vo.index.RankInfo;
import com.czxy.manage.model.vo.index.TrendInfo;
import com.czxy.manage.model.vo.student.StudentAddInfo;
import com.czxy.manage.model.vo.student.StudentPageParam;
import com.czxy.manage.model.vo.student.StudentUpdateInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

@Mapper
public interface StudentMapper {
    List<StudentDetailEntity> query(StudentPageParam<String> pageParam);

    Boolean sign(@Param("studentIds") List<Integer> studentIds);

    Boolean delete(@Param("studentIds") List<Integer> studentIds);

    Integer insert(StudentEntity studentEntity);

    Integer update(StudentEntity studentEntity);

    StudentUpdateEntity queryByStudentId(StudentEntity studentEntity);

    Integer batchInsert(List<StudentEntity> list);

    Integer clearLeader(Integer classId);

    Integer setLeader(Integer userId, Integer classId);

    Integer updateByUserId(Integer userId);

    Boolean queryByUserId(Integer userId);

    Integer updateClass(List<StudentAddInfo> studentAddInfos);

    Integer updateStudentClass(Integer classId,@Param("studentIds") List<Integer> studentIds);

    StudentEntity queryStudent(Integer userId);

    List<RankInfo> queryByTime(Date beginTime, Date endTime);
}
