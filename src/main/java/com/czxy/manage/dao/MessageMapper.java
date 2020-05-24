package com.czxy.manage.dao;

import com.czxy.manage.model.entity.SendEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MessageMapper {
    Integer batchInsert(List<SendEntity> list);
}
