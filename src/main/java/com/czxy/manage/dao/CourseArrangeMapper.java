package com.czxy.manage.dao;

import com.czxy.manage.model.entity.CourseArrangeEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CourseArrangeMapper {
    Integer batchInsert(List<CourseArrangeEntity> courseArrangeEntities);

    Integer deleteByArrangeId(@Param("arrangeId") Integer arrangeId);

    Integer queryRecentClassIdByUserId(@Param("userId") Integer userId);

}
