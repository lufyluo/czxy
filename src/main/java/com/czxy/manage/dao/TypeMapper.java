package com.czxy.manage.dao;

import com.czxy.manage.model.entity.TypeEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TypeMapper {
    List<TypeEntity> queryAll(List<Integer> topicIds);
}
