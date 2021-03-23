package com.czxy.manage.dao;

import com.czxy.manage.infrastructure.response.BaseResponse;
import com.czxy.manage.model.PageParam;
import com.czxy.manage.model.entity.*;
import com.czxy.manage.model.vo.classes.ClassInfo;
import com.czxy.manage.model.vo.classes.ClassPageParam;
import com.czxy.manage.model.vo.index.RankInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

@Mapper
public interface ClassMapper {
    List<ClassOrgEntity> queryAll(ClassPageParam param);

    Integer delete(@Param("id") List<Integer> id);

    ClassInformationEntity query(@Param("id") Integer id);

    List<ClassStudentEntity> queryAllStudent(@Param("classId") Integer classId);

    Integer insert(ClassEntity classEntity);

    Integer update(ClassEntity classEntity);

    Integer queryByName(@Param("className")String className);

    ClassEntity queryClass(@Param("classId") Integer classId);

    Integer queryRecentByStudentUserId(@Param("userId")Integer userId);

    Integer clearStudent(@Param("classId")Integer classId);

    List<CountEntity> queryCount(@Param("collect") List<Integer> collect);

    List<RankInfo> queryByTime(@Param("beginTime") Date beginTime,@Param("endTime") Date endTime);
}
