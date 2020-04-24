package com.czxy.manage.dao;

import com.czxy.manage.infrastructure.response.BaseResponse;
import com.czxy.manage.model.entity.ClassEntity;
import com.czxy.manage.model.entity.ClassOrgEntity;
import com.czxy.manage.model.vo.classes.ClassInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ClassMapper {
    List<ClassOrgEntity> queryAll(String param);

    Integer delete(Integer id);

    List<ClassInfo> query(Integer id);
}
