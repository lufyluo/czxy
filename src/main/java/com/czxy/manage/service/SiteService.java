package com.czxy.manage.service;

import com.czxy.manage.dao.SiteMapper;
import com.czxy.manage.infrastructure.util.PojoMapper;
import com.czxy.manage.model.entity.SiteEntity;
import com.czxy.manage.model.entity.TypeEntity;
import com.czxy.manage.model.vo.site.SiteAddInfo;
import com.czxy.manage.model.vo.site.SitePageInfo;
import com.czxy.manage.model.vo.site.SitePageParam;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SiteService {
    @Resource
    private SiteMapper siteMapper;
    @Resource
    private TypeService typeService;
    @Transactional
    public Boolean add(SiteAddInfo siteAddInfo) {
        SiteEntity siteEntity = prepare(siteAddInfo);
        siteMapper.insert(siteEntity);

        return true;
    }

    public Boolean delete(List<Integer> siteIds) {
        Boolean delete = siteMapper.delete(siteIds);
        return delete;
    }

    public PageInfo<SitePageInfo> page(SitePageParam<String> pageParam) {
        Page page = PageHelper.startPage(pageParam.getPageIndex(), pageParam.getPageSize());
        siteMapper.query(pageParam);
        return null;
    }

    public Boolean update(SiteAddInfo siteAddInfo) {
        SiteEntity siteEntity = prepare(siteAddInfo);
        siteMapper.update(siteEntity);
        return true;
    }

    private SiteEntity prepare(SiteAddInfo siteAddInfo){
        SiteEntity siteEntity = PojoMapper.INSTANCE.toSiteEntity(siteAddInfo);
        if (siteAddInfo.getTypes() != null && siteAddInfo.getTypes().size() > 0) {
            List<TypeEntity> typeEntityList = PojoMapper.INSTANCE.toTypeEntities(siteAddInfo.getTypes());
            typeService.batchInsertIfObsent(typeEntityList);
            siteEntity.setTypes(typeEntityList.stream().map(n -> n.getId().toString()).collect(Collectors.joining(",")));
        }
        if (siteAddInfo.getTopics() != null && siteAddInfo.getTopics().size() > 0) {
            List<TypeEntity> typeEntityList = PojoMapper.INSTANCE.toTypeEntities(siteAddInfo.getTopics());
            typeService.batchInsertIfObsent(typeEntityList);
            siteEntity.setTopics(typeEntityList.stream().map(n -> n.getId().toString()).collect(Collectors.joining(",")));
        }
        return siteEntity;
    }
}
