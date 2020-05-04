package com.czxy.manage.dao;

import com.czxy.manage.model.entity.TypeEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TypeMapper {
    List<TypeEntity> queryAll(List<Integer> topicIds);


    Integer insert(TypeEntity typeEntity);

    Integer batchInsert(List<TypeEntity> typeAddEntities);

    String query(@Param("parseInt") Integer parseInt);
}
