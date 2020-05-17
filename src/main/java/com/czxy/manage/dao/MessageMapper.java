package com.czxy.manage.dao;

import com.czxy.manage.model.entity.SendEntity;
import com.czxy.manage.model.vo.SendInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MessageMapper {

    Integer insertAll(SendEntity sendEntity);

    Integer insert(SendEntity sendEntity);
}
