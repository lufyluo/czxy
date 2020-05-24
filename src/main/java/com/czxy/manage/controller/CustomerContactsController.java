package com.czxy.manage.controller;

import com.czxy.manage.infrastructure.response.BaseResponse;
import com.czxy.manage.infrastructure.response.PageResponse;
import com.czxy.manage.infrastructure.response.ResponseUtil;
import com.czxy.manage.model.PageParam;
import com.czxy.manage.model.vo.customer.CustomerCreateInfo;
import com.czxy.manage.model.vo.customer.CustomerInfo;
import com.czxy.manage.model.vo.customer.CustomerPageParam;
import com.czxy.manage.model.vo.customerContacts.CustomerContactsCreateInfo;
import com.czxy.manage.model.vo.customerContacts.CustomerContactsInfo;
import com.czxy.manage.model.vo.customerContacts.CustomerContactsUpdateInfo;
import com.czxy.manage.service.CustomerContactsService;
import com.czxy.manage.service.CustomerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customer/contacts")
@Api(tags = "客户通讯录", value = "客户通讯录")
public class CustomerContactsController {
    @Autowired
    private CustomerContactsService customerService;

    @GetMapping("/page")
    @ApiOperation("分页获取客户通讯录信息")
    public PageResponse<CustomerContactsInfo> page(PageParam<String> pageParam) {
        return PageResponse.success(customerService.page(pageParam));
    }

    @PutMapping
    @ApiOperation("编辑")
    public BaseResponse<Boolean> update(@RequestBody CustomerContactsUpdateInfo customerInfo) {
        return ResponseUtil.success(customerService.update(customerInfo));
    }

    @PostMapping
    @ApiOperation("添加")
    public BaseResponse<Boolean> add(@RequestBody CustomerContactsCreateInfo customerInfo) {
        return ResponseUtil.success(customerService.add(customerInfo));
    }

    @DeleteMapping("/{ids}")
    @ApiOperation("批量删除")
    public BaseResponse<Boolean> delete(@PathVariable List<Integer> ids) {
        return ResponseUtil.success(customerService.delete(ids));
    }
}
