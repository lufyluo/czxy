package com.czxy.manage.dao;

import com.czxy.manage.model.entity.OrgEntity;
import com.czxy.manage.model.vo.customer.CustomerInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OrgMapper {
    Integer insertOrg(OrgEntity orgEntity);

    Integer update(OrgEntity orgEntity);

    OrgEntity query(@Param("orgId") int orgId);

    List<OrgEntity> queryAll(@Param("param") String param);

    List<OrgEntity> getByNames(@Param("list") List<String> list);

    Integer batchInsert(List<OrgEntity> orgEntities);

    Integer updateStarAndName(CustomerInfo customerInfo);

    Integer updateStar(@Param("orgId") Integer orgId, @Param("orgStar") Integer orgStar);

    OrgEntity queryByNames(@Param("orgName") String orgName);

    Integer updateAll(OrgEntity orgEntity);
}
