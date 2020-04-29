package com.czxy.manage.controller;

import com.czxy.manage.infrastructure.response.BaseResponse;
import com.czxy.manage.infrastructure.response.ResponseUtil;
import com.czxy.manage.model.vo.site.SiteAddInfo;
import com.czxy.manage.service.SiteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api("点位管理")
@RequestMapping("/api/site")
public class SiteController {
    @Autowired
    private SiteService siteService;
    @PostMapping("/add")
    @ApiOperation("新增点位")
    public BaseResponse<Boolean> add(@RequestBody SiteAddInfo siteAddInfo){
        return ResponseUtil.success(siteService.add(siteAddInfo));
    }
}
