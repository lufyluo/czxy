package com.czxy.manage.service;

import com.czxy.manage.dao.OptionMapper;
import com.czxy.manage.dao.StemMapper;
import com.czxy.manage.infrastructure.util.PojoMapper;
import com.czxy.manage.model.PageParam;
import com.czxy.manage.model.entity.PlanEntity;
import com.czxy.manage.model.entity.questionnaire.stem.OptionEntity;
import com.czxy.manage.model.entity.questionnaire.stem.StemEntity;
import com.czxy.manage.model.vo.plan.PlanInfo;
import com.czxy.manage.model.vo.questionnaire.stem.StemInfo;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
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
        List<StemEntity> stemEntities = stemMapper.query(pageParam);
        PageInfo<StemInfo> pageInfo = page.toPageInfo();
        List<StemInfo> stemInfos = PojoMapper.INSTANCE.toStemInfos(stemEntities);
        pageInfo.setList(stemInfos);
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
            optionMapper.batchUpdate(updateOptions);
        }
    }

    public List<StemInfo> get(Integer paperId) {
        List<StemEntity> stemEntities = stemMapper.queryByPaperId(paperId);
        List<StemInfo> stemInfos = PojoMapper.INSTANCE.toStemInfos(stemEntities);
        return stemInfos;
    }
}
