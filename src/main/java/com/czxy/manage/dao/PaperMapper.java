package com.czxy.manage.dao;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PaperMapper {
    Integer updateState(Integer paperId, int state);
}

