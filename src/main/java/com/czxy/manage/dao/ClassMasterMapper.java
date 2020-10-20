package com.czxy.manage.dao;

import com.czxy.manage.model.entity.ClassMasterEntity;
import com.czxy.manage.model.vo.teacher.MasterInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ClassMasterMapper {
    Integer insertMaster(Integer userId, Integer classId,Integer type);

    Integer queryClass(Integer userId);

    Integer delete(Integer masterId, Integer classId);

    Integer clearClassAssistant(Integer classId);

    List<MasterInfo> queryMasterAssistants(List<Integer> classIds);
}
