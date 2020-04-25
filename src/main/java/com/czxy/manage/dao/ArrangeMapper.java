package com.czxy.manage.dao;

import com.czxy.manage.model.entity.ClassArrangeWithTimeEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ArrangeMapper {
    ClassArrangeWithTimeEntity get(Integer classId);
}
