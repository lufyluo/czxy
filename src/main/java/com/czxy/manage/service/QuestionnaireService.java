package com.czxy.manage.service;

import com.czxy.manage.dao.*;
import com.czxy.manage.infrastructure.util.PojoMapper;
import com.czxy.manage.model.PageParam;
import com.czxy.manage.model.entity.PaperEntity;
import com.czxy.manage.model.entity.StudentDetailEntity;
import com.czxy.manage.model.entity.questionnaire.PaperSendEntity;
import com.czxy.manage.model.vo.PaperAddInfo;
import com.czxy.manage.model.vo.PaperInfo;
import com.czxy.manage.model.vo.questionnaire.PaperCreateInfo;
import com.czxy.manage.model.vo.questionnaire.PaperPublisInfo;
import com.czxy.manage.model.vo.student.GetAllParam;
import com.czxy.manage.model.vo.student.StudentPageParam;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class QuestionnaireService {
    @Resource
    private QuestionnaireMapper questionnaireMapper;
    @Resource
    private PaperStemMapper paperStemMapper;
    @Resource
    private PaperSendMapper paperSendMapper;
    @Resource
    private PaperMapper paperMapper;
    @Autowired
    private StudentService studentService;

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
        for (int i = 0; i < paperAddInfos.size(); i++) {
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
        for (int i = 0; i < paperAddInfos.size(); i++) {
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

    public Boolean down(List<Integer> paperIds) {
        questionnaireMapper.updateState(paperIds, 2);
        return true;
    }

    public Boolean publish(PaperPublisInfo paperPublisInfo) {
        Integer paperId = paperPublisInfo.getPaperId();
        GetAllParam param = PojoMapper.INSTANCE.toGetAllParam(paperPublisInfo);
        List<StudentDetailEntity> studentDetailEntities = studentService.getAllUser(param);
        List<PaperSendEntity> collect = studentDetailEntities.stream().map(n -> {
            PaperSendEntity paperSendEntityTemp = new PaperSendEntity();
            paperSendEntityTemp.setUserId(n.getUserId());
            paperSendEntityTemp.setPaperId(paperId);
            paperSendEntityTemp.setIsToAll(paperPublisInfo.getIsToAll());
            return paperSendEntityTemp;
        }).collect(Collectors.toList());
        batchInsert(collect);
        paperMapper.updateState(paperId,1);
        return true;
    }

    /**
     * 批次操作，适应多数据
     */
    private void batchInsert(List<PaperSendEntity> list) {
        int batchNum = 400;
        for (int i = 0; i < list.size() / batchNum + 1; i++) {
            int pageSize =
                    batchNum + i * batchNum >= list.size() ? list.size()  : batchNum + i * batchNum;
            paperSendMapper.batchInsert(list.subList(i, pageSize));
            if (i * batchNum >= list.size()) {
                break;
            }
        }
    }


}
