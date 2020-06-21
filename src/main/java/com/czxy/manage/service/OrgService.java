package com.czxy.manage.service;

import com.czxy.manage.dao.OrgMapper;
import com.czxy.manage.infrastructure.util.PojoMapper;
import com.czxy.manage.model.PageParam;
import com.czxy.manage.model.entity.ClassOrgEntity;
import com.czxy.manage.model.entity.OrgEntity;
import com.czxy.manage.model.vo.OrgInfo;
import com.czxy.manage.model.vo.classes.ClassOrgInfo;
import com.czxy.manage.model.vo.user.UserCreateInfo;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrgService {
    @Resource
    private OrgMapper orgMapper;

    public Integer insertIfAbsentOrg(String orgName, Integer orgId) {
        if (orgId == null && !StringUtils.isEmpty(orgName)) {
            OrgEntity orgEntity = orgMapper.queryByNames(orgName);
            if (orgEntity != null) {
                return orgEntity.getId();
            }
            orgEntity = new OrgEntity();
            orgEntity.setName(orgName);
            orgMapper.insertOrg(orgEntity);
            return orgEntity.getId();
        }
        return orgId;
    }

    public Integer insertIfAbsentOrg(OrgEntity orgEntity) {
        if (orgEntity.getId() == null && !StringUtils.isEmpty(orgEntity.getName())) {
            OrgEntity orgEntitytemp = orgMapper.queryByNames(orgEntity.getName());
            if (orgEntitytemp != null) {
                return orgEntitytemp.getId();
            }
            orgMapper.insertOrg(orgEntity);
            return orgEntity.getId();
        }
        return orgEntity.getId();
    }

    public PageInfo<OrgInfo> page(PageParam<String> pageParam) {
        Page page = PageHelper.startPage(pageParam.getPageIndex(), pageParam.getPageSize());
        List<OrgEntity> orgEntities = orgMapper.queryAll(pageParam.getParam());
        PageInfo<OrgInfo> result = page.toPageInfo();
        result.setList(PojoMapper.INSTANCE.toOrgInfos(orgEntities));
        return result;
    }

    public List<OrgEntity> batchInsertIfAbsentOrg(@NotNull List<String> orgNames) {
        orgNames = orgNames.stream().distinct().collect(Collectors.toList());
        List<OrgEntity> orgs = orgMapper.getByNames(orgNames);
        if (orgs != null && orgs.size() > 0) {
            List<String> orgExists = orgs.stream().map(OrgEntity::getName).collect(Collectors.toList());
            orgNames.removeAll(orgExists);
        }
        List<OrgEntity> collect = orgNames
                .stream()
                .map(n -> {
                    OrgEntity orgEntity = new OrgEntity();
                    orgEntity.setName(n);
                    return orgEntity;
                }).collect(Collectors.toList());

        orgMapper.batchInsert(collect);
        if (orgs != null && orgs.size() > 0) {
            collect.addAll(orgs);
        }
        return collect;
    }
}
