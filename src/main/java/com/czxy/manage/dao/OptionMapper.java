package com.czxy.manage.dao;

import com.czxy.manage.model.entity.questionnaire.stem.OptionEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OptionMapper {
    Integer batchInsert(List<OptionEntity> optionEntities);

    Integer deleteByStemId(@Param("stemId") Integer stemId);

    Integer batchUpdate(List<OptionEntity> updateOptions);

    Integer update(OptionEntity optionEntity);

    Integer deleteOptions(@Param("optionIds") List<Integer> optionIds, @Param("stemId") Integer stemId);
}
