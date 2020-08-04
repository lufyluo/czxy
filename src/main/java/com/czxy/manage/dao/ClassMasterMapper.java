package com.czxy.manage.dao;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ClassMasterMapper {
    Integer insertMaster(Integer userId, Integer classId);

    Integer queryClass(Integer userId);

    Integer delete(Integer masterId, Integer classId);
}
