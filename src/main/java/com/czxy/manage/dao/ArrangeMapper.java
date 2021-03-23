package com.czxy.manage.dao;

import com.czxy.manage.model.entity.ArrangeEntity;
import com.czxy.manage.model.entity.ClassArrangeWithTimeEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ArrangeMapper {
    ClassArrangeWithTimeEntity get(@Param("classId") Integer classId);

    Integer insert(ArrangeEntity arrangeEntity);

    List<ArrangeEntity> page(@Param("param") String param);

    Integer delete(@Param("arrangeIds") List<Integer> arrangeIds);

    ClassArrangeWithTimeEntity getById(@Param("id") Integer id);

    Integer update(ArrangeEntity arrangeEntity);
}
