package com.czxy.manage.dao;

import com.czxy.manage.model.entity.CourseArrangeEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CourseArrangeMapper {
    Integer batchInsert(List<CourseArrangeEntity> courseArrangeEntities);

    Integer deleteByArrangeId(Integer arrangeId);

    Integer queryRecentClassIdByUserId(Integer userId);

}
