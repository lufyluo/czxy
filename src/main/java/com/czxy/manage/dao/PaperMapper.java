package com.czxy.manage.dao;

import com.czxy.manage.model.entity.PaperEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PaperMapper {
    Integer updateState(Integer paperId, int state);

    PaperEntity query(Integer paperId);
}

