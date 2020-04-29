package com.czxy.manage.service;

import com.czxy.manage.dao.SiteMapper;
import com.czxy.manage.dao.TypeMapper;
import com.czxy.manage.infrastructure.util.PojoMapper;
import com.czxy.manage.model.entity.SiteEntity;
import com.czxy.manage.model.entity.TypeEntity;
import com.czxy.manage.model.vo.site.SiteAddInfo;
import org.apache.logging.log4j.core.util.ArrayUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SiteService {
    @Resource
    private SiteMapper siteMapper;
    @Resource
    private TypeService typeService;

    public Boolean add(SiteAddInfo siteAddInfo) {
        SiteEntity siteEntity = PojoMapper.INSTANCE.toSiteEntity(siteAddInfo);
        if (siteAddInfo.getTypes() != null && siteAddInfo.getTypes().size() > 0) {
            List<TypeEntity> typeEntityList = PojoMapper.INSTANCE.siteAddInfoToTypes(siteAddInfo.getTypes());
            typeService.batchInsertIfObsent(typeEntityList);
            siteEntity.setTypes(typeEntityList.stream().map(n-> n.getId().toString()).collect(Collectors.joining(",")));
        }
        if(siteAddInfo.getTopics() != null && siteAddInfo.getTopics().size() > 0)
        {
            List<TypeEntity> typeEntityList = PojoMapper.INSTANCE.siteAddInfoToTypes(siteAddInfo.getTopics());
            typeService.batchInsertIfObsent(typeEntityList);
            siteEntity.setTopics(typeEntityList.stream().map(n-> n.getId().toString()).collect(Collectors.joining(",")));
        }
        siteMapper.insert(siteEntity);

        return true;
    }
}
