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
    private TypeMapper typeMapper;

    public Boolean add(SiteAddInfo siteAddInfo) {
        SiteEntity siteEntity = PojoMapper.INSTANCE.toSiteEntity(siteAddInfo);
        if(siteAddInfo.getTypes()!=null&&siteAddInfo.getTypes().size()>0){
            List<TypeEntity> typeEntityList = PojoMapper.INSTANCE.siteAddInfoToTypes(siteAddInfo.getTypes());
            List<TypeEntity> typeAddEntities = typeEntityList
                    .stream()
                    .filter(n->n.getId()==null&& !StringUtils.isEmpty(n.getName()))
                    .collect(Collectors.toList());
             for (TypeEntity typeEntity:typeAddEntities){
               typeMapper.insert(typeEntity);
             }

            if(typeAddEntities.size() < typeEntityList.size()){
                typeAddEntities.addAll(typeEntityList.stream().filter(n->n.getId()!=null).collect(Collectors.toList()));
            }
            Arrays.toString(typeAddEntities.stream().map(TypeEntity::getId).collect(Collectors.toList()));
            siteEntity.setTypes(.);
        }

        siteMapper.insert(siteEntity);

        return true;
    }
}
