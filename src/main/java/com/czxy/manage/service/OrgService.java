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
import java.util.List;

@Service
public class OrgService {
    @Resource
    private OrgMapper orgMapper;
    public Integer insertIfAbsentOrg(String orgName,Integer orgId) {
        if (orgId == null && !StringUtils.isEmpty(orgName)) {
            OrgEntity orgEntity =orgMapper.queryByNames(orgName);
            if(orgEntity!=null){
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
}
