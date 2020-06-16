package com.czxy.manage.controller;

import com.czxy.manage.infrastructure.aop.Anonymous;
import com.czxy.manage.infrastructure.aop.FileAnonymous;
import com.czxy.manage.infrastructure.response.BaseResponse;
import com.czxy.manage.infrastructure.response.PageResponse;
import com.czxy.manage.infrastructure.response.ResponseUtil;
import com.czxy.manage.model.vo.site.SiteAddInfo;
import com.czxy.manage.model.vo.site.SiteDetailInfo;
import com.czxy.manage.model.vo.site.SiteInfo;
import com.czxy.manage.model.vo.site.SitePageParam;
import com.czxy.manage.service.SiteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@Api(tags = "点位管理", value = "点位管理")
@RequestMapping("/api/site")
public class SiteController {

    @Autowired
    private SiteService siteService;

    @PostMapping("/add")
    @ApiOperation("新增点位")
    public BaseResponse<Boolean> add(@RequestBody SiteAddInfo siteAddInfo) {
        return ResponseUtil.success(siteService.add(siteAddInfo));
    }

    @DeleteMapping("/{siteIds}")
    @ApiOperation("删除点位")
    public BaseResponse<Boolean> delete(@PathVariable List<Integer> siteIds) {
        return ResponseUtil.success(siteService.delete(siteIds));
    }

    @GetMapping("/page")
    @Anonymous
    @ApiOperation("分页获取点位")
    public PageResponse<SiteInfo> page(SitePageParam<String> pageParam) {
        return PageResponse.success(siteService.page(pageParam));
    }

    @GetMapping("/{id}")
    @Anonymous
    @ApiOperation("获取点位详情")
    public BaseResponse<SiteDetailInfo> get(@PathVariable Integer id) {
        return ResponseUtil.success(siteService.get(id));
    }

    @PutMapping
    @ApiOperation("编辑点位")
    public BaseResponse<Boolean> update(@RequestBody SiteAddInfo siteAddInfo) {
        return ResponseUtil.success(siteService.update(siteAddInfo));
    }

    @ApiOperation("导入")
    @RequestMapping(path = "/import", method = RequestMethod.POST)
    @FileAnonymous
    public BaseResponse<Boolean> batchImport(@RequestParam(value = "file") MultipartFile file) {
        return ResponseUtil.success(siteService.batchImport(file));
    }
}
