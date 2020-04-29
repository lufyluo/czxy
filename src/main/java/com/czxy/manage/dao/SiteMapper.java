package com.czxy.manage.dao;

import com.czxy.manage.model.entity.SiteEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SiteMapper {

    Integer insert(SiteEntity siteEntity);
}
