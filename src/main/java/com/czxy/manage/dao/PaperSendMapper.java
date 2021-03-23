package com.czxy.manage.dao;

import com.czxy.manage.model.entity.questionnaire.PaperSendEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PaperSendMapper {

    Integer batchInsert(List<PaperSendEntity> asList);

    int countByPaperId(@Param("paperId") Integer paperId);
}
