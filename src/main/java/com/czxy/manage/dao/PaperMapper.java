package com.czxy.manage.dao;

import com.czxy.manage.model.entity.PaperEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface PaperMapper {
    Integer updateState(@Param("paperId") Integer paperId, @Param("state") int state);

    PaperEntity query(@Param("paperId") Integer paperId);
}

