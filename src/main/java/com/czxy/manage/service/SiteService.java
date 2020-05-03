package com.czxy.manage.service;

import com.czxy.manage.dao.SiteMapper;
import com.czxy.manage.dao.TypeMapper;
import com.czxy.manage.infrastructure.util.PojoMapper;
import com.czxy.manage.model.PageParam;
import com.czxy.manage.model.entity.SiteEntity;
import com.czxy.manage.model.entity.TypeEntity;
import com.czxy.manage.model.vo.classes.ClassOrgInfo;
import com.czxy.manage.model.vo.site.SiteInfo;
import com.czxy.manage.model.vo.site.SitePageInfo;
import com.czxy.manage.model.vo.site.SitePageParam;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SiteService {
    @Resource
    private SiteMapper siteMapper;
    @Resource
    private TypeService typeService;
    @Resource
    private TypeMapper typeMapper;

    @Transactional
    public Boolean add(SiteInfo siteInfo) {
        SiteEntity siteEntity = PojoMapper.INSTANCE.toSiteEntity(siteInfo);
        if (siteInfo.getTypes() != null && siteInfo.getTypes().size() > 0) {
            List<TypeEntity> typeEntityList = PojoMapper.INSTANCE.toTypeEntities(siteInfo.getTypes());
            typeService.batchInsertIfObsent(typeEntityList);
            siteEntity.setTypes(typeEntityList.stream().map(n -> n.getId().toString()).collect(Collectors.joining(",")));
        }
        if (siteInfo.getTopics() != null && siteInfo.getTopics().size() > 0) {
            List<TypeEntity> typeEntityList = PojoMapper.INSTANCE.toTypeEntities(siteInfo.getTopics());
            typeService.batchInsertIfObsent(typeEntityList);
            siteEntity.setTopics(typeEntityList.stream().map(n -> n.getId().toString()).collect(Collectors.joining(",")));
        }
        siteMapper.insert(siteEntity);

        return true;
    }

    public Boolean delete(List<Integer> siteIds) {
        Boolean delete = siteMapper.delete(siteIds);
        return delete;
    }

    public PageInfo<SiteInfo> page(PageParam<String> pageParam) {
        Page page = PageHelper.startPage(pageParam.getPageIndex(), pageParam.getPageSize());
        List<SiteEntity> siteEntities = siteMapper.query(pageParam.getParam());
        List<TypeEntity> typeEntityList = new ArrayList<TypeEntity>();
        for (int i = 0; i <= siteEntities.size(); i++) {
            SiteEntity siteEntity = siteEntities.get(i);
            String types = siteEntity.getTypes();
            String[] splitTypes = types.split(",");
            for (int j = 0; j < splitTypes.length; j++) {
                Integer parseInt = Integer.parseInt(splitTypes[j]);
                TypeEntity typeEntity = typeMapper.query(parseInt);
                typeEntityList.add(typeEntity);
            }
            String topics = siteEntity.getTopics();
            String[] split = topics.split(",");
            for (int k = 0; k < split.length; k++) {
                Integer topicsId = Integer.parseInt(split[k]);
                TypeEntity typeEntity = typeMapper.query(topicsId);
                typeEntityList.add(typeEntity);
            }
        }
        PageInfo<SiteInfo> result = page.toPageInfo();
        return null;
    }

    public Boolean update(SiteInfo siteInfo) {
        SiteEntity siteEntity = PojoMapper.INSTANCE.toSiteEntity(siteInfo);
        if (siteInfo.getTypes() != null && siteInfo.getTypes().size() > 0) {
            List<TypeEntity> typeEntityList = PojoMapper.INSTANCE.toTypeEntities(siteInfo.getTypes());
            typeService.batchInsertIfObsent(typeEntityList);
            siteEntity.setTypes(typeEntityList.stream().map(n -> n.getId().toString()).collect(Collectors.joining(",")));
        }
        if (siteInfo.getTopics() != null && siteInfo.getTopics().size() > 0) {
            List<TypeEntity> typeEntityList = PojoMapper.INSTANCE.toTypeEntities(siteInfo.getTopics());
            typeService.batchInsertIfObsent(typeEntityList);
            siteEntity.setTopics(typeEntityList.stream().map(n -> n.getId().toString()).collect(Collectors.joining(",")));
        }
        siteMapper.update(siteEntity);
        return true;
    }
}
