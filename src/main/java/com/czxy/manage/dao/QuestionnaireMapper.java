package com.czxy.manage.dao;

import com.czxy.manage.model.entity.PaperCopyEntity;
import com.czxy.manage.model.entity.PaperDetailEntity;
import com.czxy.manage.model.entity.PaperEntity;
import com.czxy.manage.model.entity.PaperPageEntity;
import com.czxy.manage.model.entity.questionnaire.PaperSubmitEntity;
import com.czxy.manage.model.entity.questionnaire.stem.OptionEntity;
import com.czxy.manage.model.entity.questionnaire.stem.PaperCopyStemEntity;
import com.czxy.manage.model.entity.questionnaire.stem.StemEntity;
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

    List<PaperPageEntity> get(String param,Integer userId);

    Boolean insertBySubmit(PaperSubmitInfo n);

    Boolean updateSend(Integer userId, Integer paperId);

    Integer batchInsert(List<PaperSubmitEntity> paperSubmitInfo);

    List<PaperDetailEntity> queryPaperDetail(Integer userId, Integer paperId);

    Integer clearAnswers(Integer paperId, Integer userId);

    PaperCopyEntity query(Integer paperId);

    Integer insertPaper(PaperEntity paperEntity);

    Integer insertStem(StemEntity stemEntity);

    Integer insertPaperStem(PaperCopyStemEntity paperCopyStemEntity);

    Integer insertOption(OptionEntity optionEntity);
}
