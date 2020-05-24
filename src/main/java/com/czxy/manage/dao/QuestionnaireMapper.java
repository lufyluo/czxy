package com.czxy.manage.dao;

import com.czxy.manage.model.entity.PaperCreateEntity;
import com.czxy.manage.model.entity.PaperEntity;
import com.czxy.manage.model.vo.PaperInfo;
import com.czxy.manage.model.vo.questionnaire.PaperCreateInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface QuestionnaireMapper {
    List<PaperEntity> page(String param);

    Integer insert(PaperCreateInfo paperInfo);

    Integer update(PaperCreateInfo paperInfo);

    Integer delete(@Param("paperIds") List<Integer> paperIds);

    void updateState(@Param("paperIds") List<Integer> paperIds, @Param("state") int state);
}
