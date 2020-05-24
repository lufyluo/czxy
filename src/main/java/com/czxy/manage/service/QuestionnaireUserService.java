package com.czxy.manage.service;

import com.czxy.manage.dao.QuestionnaireMapper;
import com.czxy.manage.infrastructure.gloable.ManageException;
import com.czxy.manage.infrastructure.response.ResponseStatus;
import com.czxy.manage.model.entity.PaperPageEntity;
import com.czxy.manage.model.vo.questionnaire.PaperPageParam;
import com.czxy.manage.model.vo.questionnaire.PaperSubmitInfo;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class QuestionnaireUserService {
    @Resource
    private QuestionnaireMapper questionnaireMapper;

    public PageInfo page(PaperPageParam<String> pageParam) {
        List<PaperPageEntity> paperPageEntities = null;
        Page page = PageHelper.startPage(pageParam.getPageIndex(), pageParam.getPageSize());
        List<Integer> paperIds = questionnaireMapper.getPaperId(pageParam.getUserId());
        if (paperIds.isEmpty()) {
            throw new ManageException(ResponseStatus.FAILURE, "用户没有收到过问卷");
        }
        paperPageEntities = questionnaireMapper.get(pageParam.getParam(), paperIds);
        PageInfo pageInfo = page.toPageInfo();
        pageInfo.setList(paperPageEntities);
        return pageInfo;
    }

    public Boolean submit(List<PaperSubmitInfo> paperSubmitInfo) {
        paperSubmitInfo.forEach(n ->questionnaireMapper.insertBySubmit(n));
        PaperSubmitInfo paperSubmitInfo1 = paperSubmitInfo.get(0);
        questionnaireMapper.updateSend(paperSubmitInfo1.getUserId(),paperSubmitInfo1.getPaperId());
        return true;
    }
}
