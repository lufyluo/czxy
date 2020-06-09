package com.czxy.manage.service;

import com.czxy.manage.dao.OptionMapper;
import com.czxy.manage.dao.StemMapper;
import com.czxy.manage.infrastructure.util.PojoMapper;
import com.czxy.manage.model.PageParam;
import com.czxy.manage.model.entity.PlanEntity;
import com.czxy.manage.model.entity.questionnaire.stem.OptionEntity;
import com.czxy.manage.model.entity.questionnaire.stem.PaperStemEntity;
import com.czxy.manage.model.entity.questionnaire.stem.StemEntity;
import com.czxy.manage.model.entity.questionnaire.stem.StemOptionEntity;
import com.czxy.manage.model.vo.plan.PlanInfo;
import com.czxy.manage.model.vo.questionnaire.stem.OptionInfo;
import com.czxy.manage.model.vo.questionnaire.stem.PaperStemInfo;
import com.czxy.manage.model.vo.questionnaire.stem.StemInfo;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuestionnaireStemService {
    @Resource
    private StemMapper stemMapper;
    @Resource
    private OptionMapper optionMapper;

    public PageInfo<StemInfo> page(PageParam<String> pageParam) {
        Page page = PageHelper.startPage(pageParam.getPageIndex(), pageParam.getPageSize());
        List<StemOptionEntity> stemEntities = stemMapper.query(pageParam);
        PageInfo<StemInfo> pageInfo = page.toPageInfo();
        List<StemInfo> stemInfos = PojoMapper.INSTANCE.toStemOptionInfos(stemEntities);
        pageInfo.setList(distinctAndFilleOptions(stemInfos));
        return pageInfo;
    }

    @Transactional
    public Boolean update(StemInfo stemUpdateInfo) {
        StemEntity stemEntity = PojoMapper.INSTANCE.toStemEntity(stemUpdateInfo);
        stemMapper.update(stemEntity);
        if (stemUpdateInfo.getOptions() != null && stemUpdateInfo.getOptions().size() > 0) {
            List<OptionEntity> optionEntities = PojoMapper.INSTANCE.toOptionEntities(stemUpdateInfo.getOptions());
            optionEntities.forEach(n -> n.setStemId(stemEntity.getId()));
            insertOrUpdate(optionEntities);
           List<Integer> optionIds = optionEntities.stream().filter(n->n.getId()!=null).map(OptionEntity::getId).collect(Collectors.toList());
            optionMapper.deleteOptions(optionIds,stemEntity.getId());
        }else{
            optionMapper.deleteByStemId(stemEntity.getId());
        }
        return true;
    }

    @Transactional
    public Integer add(StemInfo stemCreateInfo) {
        StemEntity stemEntity = PojoMapper.INSTANCE.toStemEntity(stemCreateInfo);
        stemMapper.insert(stemEntity);
        if (stemCreateInfo.getOptions() != null && stemCreateInfo.getOptions().size() > 0) {
            List<OptionEntity> optionEntities = PojoMapper.INSTANCE.toOptionEntities(stemCreateInfo.getOptions());
            optionEntities.forEach(n -> n.setStemId(stemEntity.getId()));
            optionMapper.batchInsert(optionEntities);
        } else {
            optionMapper.deleteByStemId(stemEntity.getId());
        }
        return stemEntity.getId();
    }

    public Boolean delete(List<Integer> stemIds) {
        stemMapper.delete(stemIds);
        return true;
    }

    private void insertOrUpdate(List<OptionEntity> optionEntities) {
        List<OptionEntity> newOptions = optionEntities
                .stream()
                .filter(n -> n.getId() == null)
                .collect(Collectors.toList());
        if (newOptions != null && newOptions.size() > 0) {
            optionMapper.batchInsert(newOptions);
        }

        List<OptionEntity> updateOptions = optionEntities
                .stream()
                .filter(n -> n.getId() != null)
                .collect(Collectors.toList());
        if (updateOptions != null && updateOptions.size() > 0) {
            updateOptions.forEach(n -> {
                optionMapper.update(n);
            });
            // optionMapper.batchUpdate(updateOptions);
        }
    }

    public List<PaperStemInfo> get(Integer paperId) {
        List<PaperStemEntity> stemEntities = stemMapper.queryByPaperId(paperId);
        List<PaperStemInfo> stemInfos = PojoMapper.INSTANCE.toPaperStemInfos(stemEntities);

        return distinctAndFilleOptions1(stemInfos);
    }

    private List<PaperStemInfo> distinctAndFilleOptions1(List<PaperStemInfo> stemInfos) {
        List<PaperStemInfo> result = new ArrayList<>();
        stemInfos.forEach(n -> {
            List<PaperStemInfo> options = stemInfos
                    .stream()
                    .filter(op -> op.getStemId() == n.getId())
                    .collect(Collectors.toList());
            options.sort(Comparator.comparing(PaperStemInfo::getOptionIndex));
            if (options != null && !result.stream().anyMatch(temp -> temp.getId() == n.getId())) {
                n.setOptions(toOptionInfos1(options));
                result.add(n);
            }

        });
        return result;
    }

    private List<OptionInfo> toOptionInfos1(List<PaperStemInfo> options) {
        List<OptionInfo> optionInfos = new ArrayList<>();
        options.forEach(n -> {
            OptionInfo optionInfo = new OptionInfo();
            optionInfo.setId(n.getOptionId());
            optionInfo.setIndex(n.getOptionIndex());
            optionInfo.setName(n.getOptionName());
            optionInfo.setScore(n.getOptionScore());
            optionInfos.add(optionInfo);
        });
        return optionInfos;
    }

    private List<StemInfo> distinctAndFilleOptions(List<StemInfo> stemInfos) {
        List<StemInfo> result = new ArrayList<>();
        stemInfos.forEach(n -> {
            List<StemInfo> options = stemInfos
                    .stream()
                    .filter(op -> op.getStemId() == n.getId())
                    .collect(Collectors.toList());
            options.sort(Comparator.comparing(StemInfo::getOptionIndex));
            if (options != null && !result.stream().anyMatch(temp -> temp.getId() == n.getId())) {
                n.setOptions(toOptionInfos(options));
                result.add(n);
            }

        });
        return result;
    }

    private void addIfAbsent(List<StemInfo> result, StemInfo stemInfo) {

    }

    private List<OptionInfo> toOptionInfos(List<StemInfo> options) {
        List<OptionInfo> optionInfos = new ArrayList<>();
        options.forEach(n -> {
            OptionInfo optionInfo = new OptionInfo();
            optionInfo.setId(n.getOptionId());
            optionInfo.setIndex(n.getOptionIndex());
            optionInfo.setName(n.getOptionName());
            optionInfo.setScore(n.getOptionScore());
            optionInfos.add(optionInfo);
        });
        return optionInfos;
    }
}
