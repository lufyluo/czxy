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

    Integer clearLeader(@Param("classId") Integer classId);

    Integer setLeader(@Param("userId") Integer userId, @Param("classId") Integer classId);

    Integer updateByUserId(@Param("userId") Integer userId);

    Boolean queryByUserId(@Param("userId") Integer userId);

    Integer updateClass(List<StudentAddInfo> studentAddInfos);

    Integer updateStudentClass(@Param("classId") Integer classId, @Param("studentIds") List<Integer> studentIds);

    StudentEntity queryStudent(@Param("userId") Integer userId);

    List<RankInfo> queryByTime(@Param("beginTime") Date beginTime, @Param("endTime") Date endTime);
}
