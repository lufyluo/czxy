package com.czxy.manage.dao;

import com.czxy.manage.model.entity.PaperDetailEntity;
import com.czxy.manage.model.entity.PaperEntity;
import com.czxy.manage.model.entity.PaperPageEntity;
import com.czxy.manage.model.entity.questionnaire.PaperSendEntity;
import com.czxy.manage.model.entity.questionnaire.PaperSubmitEntity;
import com.czxy.manage.model.entity.questionnaire.stem.OptionEntity;
import com.czxy.manage.model.entity.questionnaire.stem.PaperCopyStemEntity;
import com.czxy.manage.model.entity.questionnaire.stem.PaperStemEntity;
import com.czxy.manage.model.entity.questionnaire.stem.StemEntity;
import com.czxy.manage.model.vo.questionnaire.PaperCreateInfo;
import com.czxy.manage.model.vo.questionnaire.PaperSubmitInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface QuestionnaireMapper {
    List<PaperEntity> page(@Param("param") String param);

    Integer insert(PaperCreateInfo paperInfo);

    Integer update(PaperCreateInfo paperInfo);

    Integer delete(@Param("paperIds") List<Integer> paperIds);

    void updateState(@Param("paperIds") List<Integer> paperIds, @Param("state") int state);

    List<PaperPageEntity> get(@Param("param") String param, @Param("userId") Integer userId);

    Boolean insertBySubmit(PaperSubmitInfo n);

    Boolean updateSend(@Param("userId") Integer userId, @Param("paperId") Integer paperId);

    Integer batchInsert(List<PaperSubmitEntity> paperSubmitInfo);

    List<PaperDetailEntity> queryPaperDetail(@Param("userId") Integer userId, @Param("paperId") Integer paperId);

    Integer clearAnswers(@Param("paperId") Integer paperId, @Param("userId") Integer userId);

    Integer insertPaper(PaperEntity paperEntity);

    Integer insertStem(StemEntity stemEntity);

    Integer insertPaperStem(PaperCopyStemEntity paperCopyStemEntity);

    Integer insertOption(OptionEntity optionEntity);

    PaperEntity queryPaper(@Param("paperId") Integer paperId);

    List<PaperCopyStemEntity> queryPaperStem(@Param("paperId") Integer paperId);

    StemEntity queryStem(@Param("stemId") Integer stemId);

    List<OptionEntity> queryOption(@Param("stemId") Integer stemId);

    PaperSendEntity querySendState(@Param("userId") Integer userId, @Param("paperId") Integer paperId);
}
