package com.czxy.manage.dao;

import com.czxy.manage.model.entity.PlanEntity;
import com.czxy.manage.model.vo.customer.CustomerPageParam;
import com.czxy.manage.model.vo.plan.PlanPageParam;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PlanMapper {
    List<PlanEntity> query(PlanPageParam<String> pageParam);

    Integer update(PlanEntity planEntity);

    Integer insert(PlanEntity planEntity);

    Integer delete(List<Integer> list);
}
