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

    Boolean delete(List<Integer> siteIds);

    List<SiteEntity> query(SitePageParam sitePageParam);

    List<SiteEntity> queryWithTopics(SitePageParam sitePageParam);

    Integer update(SiteEntity siteEntity);

    Integer batchInsert(List<SiteEntity> siteEntities);

    Integer insertTopic(List<TopicInfo> topics);

    Integer deleteTopic(List<Integer> siteIds);

    List<Integer> queryTopicId(@Param("id") Integer id);


    Integer updateTopic(TopicInfo topicInfo);

    Integer insertTopics(TopicInfo topicInfo);

    Integer deleteTopicById(@Param("integers") List<Integer> integers);

    List<TopicInfo> queryTopicsBySiteId(@Param("id") Integer id);

    SiteEntity queryById(@Param("id") Integer id);

    Integer clear(@Param("id") Integer id);

    List<String> queryByNames(List<String> names);

    Integer batchInsertTopics(@Param("stemId") Integer siteId, @Param("topics") String[] topics);
}
