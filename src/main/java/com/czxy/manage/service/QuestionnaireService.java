package com.czxy.manage.service;

import com.czxy.manage.dao.*;
import com.czxy.manage.infrastructure.gloable.ManageException;
import com.czxy.manage.infrastructure.response.ResponseStatus;
import com.czxy.manage.infrastructure.util.PojoMapper;
import com.czxy.manage.model.PageParam;
import com.czxy.manage.model.entity.PaperDetailEntity;
import com.czxy.manage.model.entity.PaperEntity;
import com.czxy.manage.model.entity.StudentDetailEntity;
import com.czxy.manage.model.entity.questionnaire.PaperSendEntity;
import com.czxy.manage.model.vo.PaperAddInfo;
import com.czxy.manage.model.vo.PaperInfo;
import com.czxy.manage.model.vo.questionnaire.*;
import com.czxy.manage.model.vo.student.GetAllParam;
import com.czxy.manage.model.vo.student.StudentPageParam;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
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


    public PaperAnalysisDetailInfo analysis(Integer paperId) {
        List<PaperDetailEntity> entities = questionnaireMapper.queryPaperDetail(null, paperId);
        if (entities == null || entities.size() == 0) {
            throw new ManageException(ResponseStatus.DATANOTEXIST, "问卷不存在");
        }
        PaperAnalysisDetailInfo paperDetailInfo = PojoMapper.INSTANCE.toPaperAnalysisDetailInfo(entities.get(0));
        Map<Integer, List<PaperDetailEntity>> items = entities.stream().collect(Collectors.groupingBy(n -> n.getStemId()));
        List<StemAnalysisDetailInfo> stemDetailInfos = new ArrayList<>();
        int totalSend = paperSendMapper.countByPaperId(paperId);
        for (Map.Entry<Integer, List<PaperDetailEntity>> entry : items.entrySet()) {
            if (entry.getValue() != null && entry.getValue().size() > 0) {
                PaperDetailEntity stemInfo = entry.getValue().get(0);
                StemAnalysisDetailInfo stemDetailInfo = PojoMapper.INSTANCE.toPaperDetailEntity(stemInfo);
                stemDetailInfo.setId(entry.getKey());
                stemDetailInfo.setAnswers(getOptions(entry.getValue(),totalSend));
                stemDetailInfos.add(stemDetailInfo);
            }

        }
        paperDetailInfo.setStemDetailInfos(stemDetailInfos);
        return paperDetailInfo;
    }

    private List<OptionAnalysisDetailInfo> getOptions(List<PaperDetailEntity> options,int total) {
        List<OptionAnalysisDetailInfo> optionAnalysisDetailInfos = new ArrayList<>();
        if(ObjectUtils.nullSafeEquals("问答题",options.get(0).getType())){
            OptionAnalysisDetailInfo optionAnalysisDetailInfo = PojoMapper.INSTANCE.toOptionAnalysisDetailInfo(options.get(0));
            Long collect = options.stream().filter(n -> n.getAnswerId() != null).collect(Collectors.counting());
            float percent = (int) ((new BigDecimal((float) collect/total).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue())*100);
            optionAnalysisDetailInfo.setPercent(percent+"%");
            optionAnalysisDetailInfos.add(optionAnalysisDetailInfo);
            return optionAnalysisDetailInfos;
        }

        Map<Integer, List<PaperDetailEntity>> collect = options.stream().collect(Collectors.groupingBy(n -> n.getOptionId()));
        for (Map.Entry<Integer, List<PaperDetailEntity>> entry : collect.entrySet()) {
            if (entry.getValue() != null && entry.getValue().size() > 0) {
                OptionAnalysisDetailInfo optionAnalysisDetailInfo = PojoMapper.INSTANCE.toOptionAnalysisDetailInfo(entry.getValue().get(0));
                optionAnalysisDetailInfo.setCount(entry.getValue().size());
                Long count = options.stream().filter(n -> n.getAnswerId() != null).collect(Collectors.counting());
                float percent = (int) ((new BigDecimal((float) count/total).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue())*100);
                optionAnalysisDetailInfo.setPercent(percent+"%");
                optionAnalysisDetailInfos.add(optionAnalysisDetailInfo);
            }

        }
        return optionAnalysisDetailInfos;
    }
}
