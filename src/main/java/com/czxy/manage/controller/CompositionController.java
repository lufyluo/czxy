package com.czxy.manage.controller;

import com.czxy.manage.infrastructure.response.PageResponse;
import com.czxy.manage.model.PageParam;
import com.czxy.manage.model.vo.CompositionInfo;
import com.czxy.manage.service.CompositionService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "班级成分、培训对象",value = "班级成分、培训对象")
@RestController
@RequestMapping("/api/composition")
public class CompositionController {
    private CompositionService compositionService;
    @GetMapping("/page")
    @ApiOperation(value = "班级成分、培训对象分页获取", notes = "班级成分、培训对象分页获取")
    public PageResponse<CompositionInfo> page(PageParam<String> pageParam) {
        return PageResponse.success(compositionService.page(pageParam));
    }
}
