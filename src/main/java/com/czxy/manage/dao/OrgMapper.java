package com.czxy.manage.dao;

import com.czxy.manage.model.entity.OrgEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrgMapper {
    Integer insertOrg(OrgEntity orgEntity);
}
