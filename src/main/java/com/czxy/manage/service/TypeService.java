package com.czxy.manage.service;

import com.czxy.manage.dao.TypeMapper;
import com.czxy.manage.infrastructure.util.PojoMapper;
import com.czxy.manage.model.entity.TypeEntity;
import com.czxy.manage.model.vo.site.TypeInfo;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class TypeService {
    @Resource
    private TypeMapper typeMapper;

    public Boolean batchInsertIfObsent(List<TypeEntity> typeEntityList) {
        checkExist(typeEntityList);
        List<TypeEntity> typeAddEntities = typeEntityList
                .stream()
                .filter(n -> n.getId() == null && !StringUtils.isEmpty(n.getName()))
                .collect(Collectors.toList());
        if (typeAddEntities != null && typeAddEntities.size() > 0) {
            batchInsert(typeAddEntities);
        }

        typeEntityList.forEach(n->{
            Optional<TypeEntity> optional = typeAddEntities
                    .stream()
                    .filter(item-> ObjectUtils.nullSafeEquals(n.getName(),item.getName()))
                    .findFirst();
            if(optional.isPresent()){
                n.setId(optional.get().getId());
            }
        });
        return true;
    }

    private void checkExist(List<TypeEntity> typeEntityList) {
        List<String> names = typeEntityList.stream().map(n -> n.getName()).collect(Collectors.toList());
        List<TypeEntity> exists = typeMapper.queryByNames(names);
        typeEntityList.forEach(n->{
            Optional<TypeEntity> optional = exists.stream()
                    .filter(m->ObjectUtils.nullSafeEquals(m.getName(),n.getName())).findFirst();
            if(optional.isPresent()){
                n.setId(optional.get().getId());
            }
        });
    }

    public Boolean batchInsert(List<TypeEntity> typeAddEntities) {

        typeMapper.batchInsert(typeAddEntities);
        return true;
    }

    public List<TypeInfo> get(String key,Integer category) {
        List<TypeEntity> typeEntities = typeMapper.queryByKey(key,category);
        return PojoMapper.INSTANCE.toTypeInfos(typeEntities);
    }
}
