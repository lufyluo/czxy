package com.czxy.manage.dao;

import com.czxy.manage.model.entity.PaperCreateEntity;
import com.czxy.manage.model.entity.PaperEntity;
import com.czxy.manage.model.entity.PaperPageEntity;
import com.czxy.manage.model.vo.PaperInfo;
import com.czxy.manage.model.vo.questionnaire.PaperCreateInfo;
import com.czxy.manage.model.vo.questionnaire.PaperSubmitInfo;
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

    List<Integer> getPaperId(Integer userId);

    List<PaperPageEntity> get(String param, @Param("paperIds") List<Integer> paperIds);

    Boolean insertBySubmit(PaperSubmitInfo n);

    Boolean updateSend(Integer userId, Integer paperId);
}
