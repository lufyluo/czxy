package com.czxy.manage.controller;

import com.czxy.manage.infrastructure.response.BaseResponse;
import com.czxy.manage.infrastructure.response.ResponseUtil;
import com.czxy.manage.model.vo.IndexInfo;
import com.czxy.manage.service.IndexService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Api(tags = "首页", value = "首页")
@RequestMapping("/api/index")
public class IndexController {
    @Autowired
    private IndexService indexService;

    @GetMapping("/{userId}")
    @ApiOperation("获取首页轮播图url")
    public BaseResponse<IndexInfo> get(@PathVariable Integer userId) {
        return ResponseUtil.success(indexService.get(userId));
    }
}
