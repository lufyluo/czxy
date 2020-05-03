package com.czxy.manage.controller;

import com.czxy.manage.infrastructure.response.BaseResponse;
import com.czxy.manage.infrastructure.response.ResponseUtil;
import com.czxy.manage.model.PageParam;
import com.czxy.manage.model.vo.site.SiteAddInfo;
import com.czxy.manage.model.vo.site.SiteInfo;
import com.czxy.manage.service.SiteService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @DeleteMapping
    @ApiOperation("删除点位")
    public BaseResponse<Boolean> delete(@RequestBody List<Integer> siteIds){
        return ResponseUtil.success(siteService.delete(siteIds));
    }
    @GetMapping("/page")
    @ApiOperation("分页获取点位")
    public PageInfo<SiteInfo> page(PageParam<String> pageParam){
        return siteService.page(pageParam);
    }
    @PutMapping
    @ApiOperation("编辑点位")
    public BaseResponse<Boolean> update(@RequestBody SiteAddInfo siteAddInfo){
        return ResponseUtil.success(siteService.update(siteAddInfo));
    }
}
