package com.czxy.manage.controller;

import com.czxy.manage.model.PageParam;
import com.czxy.manage.model.vo.CompositionInfo;
import com.czxy.manage.service.CompositionService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api("培训对象")
@RestController
@RequestMapping("/api/composition")
public class CompositionController {
    private CompositionService compositionService;
    @GetMapping("/page")
    @ApiOperation(value = "分页培训对象", notes = "分页培训对象")
    public PageInfo<CompositionInfo> page(PageParam<String> pageParam) {
        return compositionService.page(pageParam);
    }
}
