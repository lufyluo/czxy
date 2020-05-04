package com.czxy.manage.dao;

import com.czxy.manage.model.entity.SiteEntity;
import com.czxy.manage.model.entity.SitePageEntity;
import com.czxy.manage.model.vo.site.SitePageParam;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SiteMapper {

    Integer insert(SiteEntity siteEntity);

    Boolean delete(@Param("siteIds") List<Integer> siteIds);

    List<SiteEntity> query( SitePageParam Param);

    Integer update(SiteEntity siteEntity);
}
