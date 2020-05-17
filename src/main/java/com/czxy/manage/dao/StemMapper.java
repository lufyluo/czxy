package com.czxy.manage.dao;

import com.czxy.manage.model.PageParam;
import com.czxy.manage.model.entity.questionnaire.stem.StemEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface StemMapper {
    Integer insert(StemEntity stemEntity);

    Integer update(StemEntity stemEntity);

    Integer delete(List<Integer> stemIds);

    List<StemEntity> query(PageParam<String> pageParam);
}
