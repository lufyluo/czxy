package com.czxy.manage.service;

import com.czxy.manage.dao.QuestionnaireMapper;
import com.czxy.manage.infrastructure.gloable.ManageException;
import com.czxy.manage.infrastructure.response.ResponseStatus;
import com.czxy.manage.infrastructure.util.PojoMapper;
import com.czxy.manage.model.entity.PaperDetailEntity;
import com.czxy.manage.model.entity.PaperPageEntity;
import com.czxy.manage.model.entity.questionnaire.PaperSubmitEntity;
import com.czxy.manage.model.vo.questionnaire.*;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class QuestionnaireUserService {
    @Resource
    private QuestionnaireMapper questionnaireMapper;

    public PageInfo page(PaperPageParam<String> pageParam) {
        Page page = PageHelper.startPage(pageParam.getPageIndex(), pageParam.getPageSize());
        List<PaperPageEntity> paperPageEntities = questionnaireMapper.get(pageParam.getParam(), pageParam.getUserId());
        PageInfo pageInfo = page.toPageInfo();
        pageInfo.setList(paperPageEntities);
        return pageInfo;
    }

    @Transactional
    public Boolean submit(List<PaperSubmitInfo> paperSubmitInfo) {
        if (paperSubmitInfo == null || paperSubmitInfo.size() == 0) {
            return true;
        }
        questionnaireMapper.clearAnswers(paperSubmitInfo.get(0).getPaperId(),paperSubmitInfo.get(0).getUserId());
        List<PaperSubmitEntity> entities = toPaperSubmitEntities(paperSubmitInfo);
        questionnaireMapper.batchInsert(entities);
        PaperSubmitInfo paperSubmitInfo1 = paperSubmitInfo.get(0);
        questionnaireMapper.updateSend(paperSubmitInfo1.getUserId(), paperSubmitInfo1.getPaperId());
        return true;
    }

    private List<PaperSubmitEntity> toPaperSubmitEntities(List<PaperSubmitInfo> paperSubmitInfo) {
        List<PaperSubmitEntity> entities = new ArrayList<>();
        paperSubmitInfo.forEach(n -> {
            if (n.getOptionIds() != null && n.getOptionIds().size() > 0) {
                n.getOptionIds().forEach(op -> {
                    PaperSubmitEntity entity = PojoMapper.INSTANCE.toPaperSubmitEntity(n);
                    entity.setOptionId(op);
                    entities.add(entity);
                });
            }
        });
        return entities;
    }

    public PaperDetailInfo paperDetail(Integer userId, Integer paperId) {
        List<PaperDetailEntity> paperDetailEntities = questionnaireMapper.queryPaperDetail(userId, paperId);
        if (paperDetailEntities == null || paperDetailEntities.size() == 0) {
            throw new ManageException(ResponseStatus.DATANOTEXIST, "问卷不存在");
        }
        PaperDetailInfo paperDetailInfo = PojoMapper.INSTANCE.toPaperDetailInfo(paperDetailEntities.get(0));
        Map<Integer, List<PaperDetailEntity>> items = paperDetailEntities.stream().collect(Collectors.groupingBy(n -> n.getStemId()));
        List<StemDetailInfo> stemDetailInfos = new ArrayList<>();
        for (Map.Entry<Integer, List<PaperDetailEntity>> entry : items.entrySet()) {
            if (entry.getValue() != null && entry.getValue().size() > 0) {
                PaperDetailEntity stemInfo = entry.getValue().get(0);
                StemDetailInfo stemDetailInfo = PojoMapper.INSTANCE.toStemDetailInfo(stemInfo);
                stemDetailInfo.setId(entry.getKey());
                stemDetailInfo.setAnswers(getOptions(entry.getValue()));
                stemDetailInfos.add(stemDetailInfo);
            }

        }
        paperDetailInfo.setStemDetailInfos(stemDetailInfos);
        return paperDetailInfo;
    }

    private List<OptionDetailInfo> getOptions(List<PaperDetailEntity> stemDetailInfos) {
        List<OptionDetailInfo> optionDetailInfos = new ArrayList<>();
        stemDetailInfos.forEach(n -> {
            OptionDetailInfo optionDetailInfo = new OptionDetailInfo();
            optionDetailInfo.setId(n.getOptionId());
            optionDetailInfo.setName(n.getOptionName());
            optionDetailInfo.setIndex(n.getOptionIndex());
            optionDetailInfo.setScore(n.getOptionScore());
            optionDetailInfo.setAnswerText(n.getAnswerText());
            optionDetailInfo.setSelected(n.getAnswerId() != null);
            optionDetailInfos.add(optionDetailInfo);
        });
        return optionDetailInfos;
    }
}
