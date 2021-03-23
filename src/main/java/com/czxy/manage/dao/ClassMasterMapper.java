package com.czxy.manage.dao;

import com.czxy.manage.model.entity.ClassMasterEntity;
import com.czxy.manage.model.vo.teacher.MasterInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ClassMasterMapper {
    Integer insertMaster(@Param("userId") Integer userId, @Param("classId") Integer classId,@Param("type") Integer type);

    Integer queryClass(@Param("userId") Integer userId);

    Integer delete(@Param("masterId")Integer masterId,@Param("classId") Integer classId);

    Integer clearClassAssistant(@Param("classId") Integer classId);

    List<MasterInfo> queryMasterAssistants(List<Integer> classIds);
}
