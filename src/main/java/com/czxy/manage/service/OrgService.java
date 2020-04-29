package com.czxy.manage.service;

import com.czxy.manage.dao.OrgMapper;
import com.czxy.manage.model.entity.OrgEntity;
import com.czxy.manage.model.vo.user.UserCreateInfo;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

@Service
public class OrgService {
    @Resource
    private OrgMapper orgMapper;
    public Integer insertIfAbsentOrg(String orgName,Integer orgId) {
        if (orgId == null && !StringUtils.isEmpty(orgName)) {
            OrgEntity orgEntity = new OrgEntity();
            orgEntity.setName(orgName);
            orgMapper.insertOrg(orgEntity);
            return orgEntity.getId();
        }
        return orgId;
    }
}
