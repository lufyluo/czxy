package com.czxy.manage.dao;

import com.czxy.manage.model.entity.CompositionEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CompositionMapper {
    Integer insert(@Param("name") String name);
    List<CompositionEntity> queryAll(@Param("param") String param);

    Integer insertEntity(CompositionEntity compositionEntity);

    CompositionEntity queryByName(@Param("composition") String composition);
}
