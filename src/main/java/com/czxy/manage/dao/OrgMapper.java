package com.czxy.manage.dao;

import com.czxy.manage.model.entity.ClassOrgEntity;
import com.czxy.manage.model.entity.OrgEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrgMapper {
    Integer insertOrg(OrgEntity orgEntity);

    Integer update(OrgEntity orgEntity);

    OrgEntity query(int orgId);

    List<OrgEntity> queryAll(String param);
}
