package com.czxy.manage.dao;

import com.czxy.manage.model.entity.questionnaire.stem.OptionEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OptionMapper {
    Integer batchInsert(List<OptionEntity> optionEntities);

    Integer deleteByStemId(Integer stemId);

    Integer batchUpdate(List<OptionEntity> updateOptions);
}
