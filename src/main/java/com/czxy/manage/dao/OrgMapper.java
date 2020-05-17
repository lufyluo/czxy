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

    OrgEntity query(int orgId);

    List<OrgEntity> queryAll(String param);

    List<OrgEntity> getByNames(@Param("list") List<String> list);

    Integer batchInsert(List<String> orgNames);

    Integer updateStarAndName(CustomerInfo customerInfo);

    Integer updateStar(Integer orgId, Integer orgStar);
}
