package com.czxy.manage.dao;

import com.czxy.manage.model.entity.SubjectDetailEntity;
import com.czxy.manage.model.entity.SubjectEntity;
import com.czxy.manage.model.vo.subject.SubjectDetailInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SubjectMapper {

    List<SubjectDetailEntity> query(String param, Integer typeId,Integer category);

    Integer add(SubjectEntity subjectEntity);

}
