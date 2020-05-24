package com.czxy.manage.dao;

import com.czxy.manage.model.entity.questionnaire.PaperSendEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PaperSendMapper {

    Integer batchInsert(List<PaperSendEntity> asList);
}
