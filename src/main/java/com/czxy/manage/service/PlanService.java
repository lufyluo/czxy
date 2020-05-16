package com.czxy.manage.service;

import com.czxy.manage.dao.PlanMapper;
import com.czxy.manage.dao.TypeMapper;
import com.czxy.manage.infrastructure.util.PojoMapper;
import com.czxy.manage.model.entity.PlanEntity;
import com.czxy.manage.model.entity.TypeEntity;
import com.czxy.manage.model.vo.customer.CustomerPageParam;
import com.czxy.manage.model.vo.plan.PlanCreateInfo;
import com.czxy.manage.model.vo.plan.PlanInfo;
import com.czxy.manage.model.vo.plan.PlanPageParam;
import com.czxy.manage.model.vo.plan.PlanUpdateInfo;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlanService {
    @Resource
    private PlanMapper planMapper;
    @Resource
    TypeMapper typeMapper;
    @Autowired
    private TypeService typeService;

    public PageInfo page(PlanPageParam<String> pageParam) {
        Page page = PageHelper.startPage(pageParam.getPageIndex(), pageParam.getPageSize());
        List<PlanEntity> planEntities = planMapper.query(pageParam);
        PageInfo<PlanInfo> pageInfo = page.toPageInfo();
        List<PlanInfo> planInfos = PojoMapper.INSTANCE.toPlanEntitys(planEntities);
        fillTypes(planInfos);
        pageInfo.setList(planInfos);
        return pageInfo;
    }
    private void fillTypes(List<PlanInfo> planInfos){
        planInfos.forEach(n->{
            List<Integer> typeIds = Arrays.stream(n.getTypes().split(","))
                    .map(item->Integer.parseInt(item))
                    .distinct().collect(Collectors.toList());
            List<TypeEntity> typeEntities = typeMapper.queryAll(typeIds);
            if(typeEntities!=null){
                n.setTypeInfos(PojoMapper.INSTANCE.toTypeInfos(typeEntities));
                n.setTypeNames(
                        typeEntities.stream()
                                .map(TypeEntity::getName)
                                .collect(Collectors.joining(",")));
            }
        });
    }

    public Boolean update(PlanUpdateInfo planUpdateInfo) {
        List<TypeEntity> typeEntityList = PojoMapper.INSTANCE.toTypeEntities(planUpdateInfo.getTypeInfos());
        PlanEntity planEntity = PojoMapper.INSTANCE.toPlanEntity(planUpdateInfo);
        if(planUpdateInfo.getTypeInfos()!=null){
            typeService.batchInsertIfObsent(typeEntityList);
            planEntity.setTypes(
                    typeEntityList.stream()
                            .map(n-> n.getId().toString())
                            .collect(Collectors.joining(",")));
        }
        if(planUpdateInfo.getFileIds() == null){
            planEntity.setFiles(null);
        }else {
            planEntity.setFiles(planUpdateInfo.getFileIds().stream().map(n->n.toString())
                    .collect(Collectors.joining(",")));
        }
        planMapper.update(planEntity);
        return true;
    }

    public Boolean add(PlanCreateInfo planCreateInfo) {
        List<TypeEntity> typeEntityList = PojoMapper.INSTANCE.toTypeEntities(planCreateInfo.getTypeInfos());
        PlanEntity planEntity = PojoMapper.INSTANCE.planCreateInfoToPlanEntity(planCreateInfo);
        if(planCreateInfo.getTypeInfos()!=null){
            typeService.batchInsertIfObsent(typeEntityList);
            planEntity.setTypes(
                    typeEntityList.stream()
                            .map(n-> n.getId().toString())
                            .collect(Collectors.joining(",")));
        }
        if(planCreateInfo.getFileIds() == null){
            planEntity.setFiles(null);
        }else {
            planEntity.setFiles(planCreateInfo.getFileIds().stream().map(n->n.toString())
                    .collect(Collectors.joining(",")));
        }
        planMapper.insert(planEntity);
        return true;
    }

    public Boolean delete(List<Integer> planIds) {
        planMapper.delete(planIds);
        return true;
    }
}
