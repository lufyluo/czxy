package com.czxy.manage.service;

import com.czxy.manage.dao.PaperStemMapper;
import com.czxy.manage.dao.QuestionnaireMapper;
import com.czxy.manage.infrastructure.util.PojoMapper;
import com.czxy.manage.model.PageParam;
import com.czxy.manage.model.entity.PaperCreateEntity;
import com.czxy.manage.model.entity.PaperEntity;
import com.czxy.manage.model.vo.PaperAddInfo;
import com.czxy.manage.model.vo.PaperInfo;
import com.czxy.manage.model.vo.plan.PlanPageParam;
import com.czxy.manage.model.vo.questionnaire.PaperCreateInfo;
import com.czxy.manage.model.vo.questionnaire.PaperUpdateInfo;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class QuestionnaireService {
    @Resource
    private QuestionnaireMapper questionnaireMapper;
    @Resource
    private PaperStemMapper paperStemMapper;

    public PageInfo<PaperInfo> page(PageParam<String> pageParam) {
        Page page = PageHelper.startPage(pageParam.getPageIndex(), pageParam.getPageSize());
        List<PaperEntity> paperEntityList = questionnaireMapper.page(pageParam.getParam());
        PageInfo pageInfo = page.toPageInfo();
        pageInfo.setList(PojoMapper.INSTANCE.toPaperInfos(paperEntityList));
        return pageInfo;
    }

    @Transactional
    public Boolean update(PaperCreateInfo paperInfo) {
        questionnaireMapper.update(paperInfo);
        paperStemMapper.delete(paperInfo.getId());
        List<PaperAddInfo> paperAddInfos = paperInfo.getPaperAddInfos();
        for (int i = 0; i <paperAddInfos.size() ; i++) {
            PaperAddInfo paperAddInfo = paperAddInfos.get(i);
            paperAddInfo.setPaperId(paperInfo.getId());
            paperStemMapper.insert(paperAddInfo);
        }
        return true;
    }

    @Transactional
    public Boolean add(PaperCreateInfo paperInfo) {
        questionnaireMapper.insert(paperInfo);
        List<PaperAddInfo> paperAddInfos = paperInfo.getPaperAddInfos();
        for (int i = 0; i <paperAddInfos.size() ; i++) {
            PaperAddInfo paperAddInfo = paperAddInfos.get(i);
            paperAddInfo.setPaperId(paperInfo.getId());
            paperStemMapper.insert(paperAddInfo);
        }
        return true;
    }

    public Boolean delete(List<Integer> paperIds) {
        questionnaireMapper.delete(paperIds);
        return true;
    }
}
