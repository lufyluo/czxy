package com.czxy.manage.controller;

import com.czxy.manage.infrastructure.response.BaseResponse;
import com.czxy.manage.infrastructure.response.PageResponse;
import com.czxy.manage.infrastructure.response.ResponseUtil;
import com.czxy.manage.model.vo.customer.CustomerPageParam;
import com.czxy.manage.model.vo.customer.CustomerCreateInfo;
import com.czxy.manage.model.vo.customer.CustomerInfo;
import com.czxy.manage.model.vo.plan.PlanCreateInfo;
import com.czxy.manage.model.vo.plan.PlanInfo;
import com.czxy.manage.model.vo.plan.PlanPageParam;
import com.czxy.manage.model.vo.plan.PlanUpdateInfo;
import com.czxy.manage.service.PlanService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/plan")
@Api(tags = "方案管理",value = "方案管理")
public class PlanController {
    @Autowired
    private PlanService planService;
    @GetMapping("/page")
    @ApiOperation("分页获取")
    public PageResponse<PlanInfo> page(PlanPageParam<String> pageParam){
        return PageResponse.success(planService.page(pageParam));
    }
    @PutMapping
    @ApiOperation("编辑")
    public BaseResponse<Boolean> update(@RequestBody PlanUpdateInfo customerInfo){
        return ResponseUtil.success(planService.update(customerInfo));
    }

    @PostMapping
    @ApiOperation("添加")
    public BaseResponse<Boolean> add(@RequestBody PlanCreateInfo customerInfo){
        return ResponseUtil.success(planService.add(customerInfo));
    }
    @DeleteMapping("/{planIds}")
    @ApiOperation("批量删除")
    public BaseResponse<Boolean> delete(@PathVariable List<Integer> planIds){
        return ResponseUtil.success(planService.delete(planIds));
    }
}
