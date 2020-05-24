package com.czxy.manage.controller;

import com.czxy.manage.infrastructure.response.BaseResponse;
import com.czxy.manage.infrastructure.response.PageResponse;
import com.czxy.manage.infrastructure.response.ResponseUtil;
import com.czxy.manage.model.PageParam;
import com.czxy.manage.model.vo.customerContacts.ContactLogInfo;
import com.czxy.manage.model.vo.customerContacts.CustomerContactsCreateInfo;
import com.czxy.manage.model.vo.customerContacts.CustomerContactsInfo;
import com.czxy.manage.model.vo.customerContacts.CustomerContactsUpdateInfo;
import com.czxy.manage.service.ContactLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contact/log")
@Api(tags = "客户通讯录联系日志", value = "客户通讯录联系日志")
public class ContactLogController {
    @Autowired
    private ContactLogService contactLogService;

    @GetMapping("/{planId}")
    @ApiOperation("获取客户通讯录联系日志信息")
    public BaseResponse<List<ContactLogInfo>> get(@PathVariable Integer planId) {
        return ResponseUtil.success(contactLogService.get(planId));
    }

    @PostMapping
    @ApiOperation("添加")
    public BaseResponse<Boolean> add(@RequestBody ContactLogInfo contactLogInfo) {
        return ResponseUtil.success(contactLogService.add(contactLogInfo));
    }
}
