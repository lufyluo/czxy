package com.czxy.manage.dao;

import com.czxy.manage.model.entity.CompositionEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CompositionMapper {
    Integer insert(String name);
    List<CompositionEntity> queryAll(String param);

    Integer insertEntity(CompositionEntity compositionEntity);

    CompositionEntity queryByName(String composition);
}
