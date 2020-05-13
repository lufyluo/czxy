package com.czxy.manage.controller;

import com.czxy.manage.infrastructure.response.BaseResponse;
import com.czxy.manage.infrastructure.response.PageResponse;
import com.czxy.manage.infrastructure.response.ResponseUtil;
import com.czxy.manage.model.vo.CustomerPageParam;
import com.czxy.manage.model.vo.student.CustomerInfo;
import com.czxy.manage.service.CustomerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customer")
@Api(tags = "平台客户管理",value = "平台客户管理")
public class CustomerController {
    @Autowired
    private CustomerService customerService;
    @GetMapping("/page")
    @ApiOperation("分页获取客户信息")
    public PageResponse<CustomerInfo> page(CustomerPageParam<String> pageParam){
     return PageResponse.success(customerService.page(pageParam));
    }
    @PutMapping
    @ApiOperation("编辑")
    public BaseResponse<Boolean> update(@RequestBody CustomerInfo customerInfo){
     return ResponseUtil.success(customerService.update(customerInfo));
    }
}
