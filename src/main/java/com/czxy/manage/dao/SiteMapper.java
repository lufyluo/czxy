package com.czxy.manage.dao;

import com.czxy.manage.model.entity.SiteEntity;
import com.czxy.manage.model.entity.SitePageEntity;
import com.czxy.manage.model.vo.site.SitePageParam;
import com.czxy.manage.model.vo.site.TopicInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SiteMapper {

    Integer insert(SiteEntity siteEntity);

    Boolean delete(@Param("siteIds") List<Integer> siteIds);

    List<SiteEntity> query( SitePageParam sitePageParam);

    Integer update(SiteEntity siteEntity);

    Integer batchInsert(List<SiteEntity> siteEntities);

    Integer insertTopic( List<TopicInfo> topics);

    Integer deleteTopic(List<Integer> siteIds);

    List<Integer> queryTopicId(Integer id);


    Integer updateTopic(TopicInfo topicInfo);

    Integer insertTopics(TopicInfo topicInfo);

    Integer deleteTopicById(@Param("integers") List<Integer> integers);

    List<TopicInfo> queryTopicsBySiteId(Integer id);

    SiteEntity queryById(Integer id);

    Integer clear(Integer id);
}
