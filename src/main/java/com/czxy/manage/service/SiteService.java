package com.czxy.manage.service;

import com.czxy.manage.dao.SiteMapper;
import com.czxy.manage.dao.TypeMapper;
import com.czxy.manage.infrastructure.util.PojoMapper;
import com.czxy.manage.model.PageParam;
import com.czxy.manage.model.entity.SiteEntity;
import com.czxy.manage.model.entity.TypeEntity;
import com.czxy.manage.model.vo.site.SiteAddInfo;
import com.czxy.manage.model.vo.site.SiteInfo;
import com.czxy.manage.model.vo.site.SitePageAddInfo;
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
    public Boolean add(SiteAddInfo siteAddInfo) {
        SiteEntity siteEntity = prepare(siteAddInfo);
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
        for (int i = 0; i < siteEntities.size(); i++) {
            SiteEntity siteEntity = siteEntities.get(i);
            String types = siteEntity.getTypes();
            String[] split = types.split(",");
            List<String> typeNames= new ArrayList<>();
            List<String> topicsNames= new ArrayList<>();
            for (int j = 0; j < split.length; j++) {
                Integer m = Integer.parseInt(split[j]);
                String typeName = typeMapper.query(m);
                typeNames.add(typeName);
            }
            String typeName = String.join(",", typeNames);
            siteEntity.setTypeName(typeName);
            String topics = siteEntity.getTopics();
            String[] typeIds = topics.split(",");
            for (int k = 0; k <typeIds.length ; k++) {
                Integer n = Integer.parseInt(typeIds[k]);
                String topicsName = typeMapper.query(n);
                topicsNames.add(topicsName);
            }
            String topicsName = String.join(",", topicsNames);
            siteEntity.setTopicsName(topicsName);
        }
        PageInfo<SiteInfo> result = page.toPageInfo();
        result.setList(PojoMapper.INSTANCE.toSiteInfo(siteEntities));
        return result;
    }

    public Boolean update(SiteAddInfo siteAddInfo) {
        SiteEntity siteEntity = prepare(siteAddInfo);
        siteMapper.update(siteEntity);
        return true;
    }

    private SiteEntity prepare(SiteAddInfo siteAddInfo) {
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
