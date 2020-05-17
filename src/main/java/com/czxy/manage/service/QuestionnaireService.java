package com.czxy.manage.service;

import com.czxy.manage.model.PageParam;
import com.czxy.manage.model.vo.questionnaire.PaperCreateInfo;
import com.czxy.manage.model.vo.questionnaire.PaperUpdateInfo;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionnaireService {
    public PageInfo<PageInfo> page(PageParam<String> pageParam) {
        return null;
    }

    public Boolean update(PaperUpdateInfo paperInfo) {
        return null;
    }

    public Boolean add(PaperCreateInfo paperInfo) {
        return null;
    }

    public Boolean delete(List<Integer> paperIds) {
        return null;
    }
}
