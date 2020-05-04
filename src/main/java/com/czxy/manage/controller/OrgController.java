package com.czxy.manage.controller;

import com.czxy.manage.model.PageParam;
import com.czxy.manage.model.vo.OrgInfo;
import com.czxy.manage.model.vo.classes.ClassOrgInfo;
import com.czxy.manage.service.OrgService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "单位",value = "单位")
@RestController
@RequestMapping("/api/org")
public class OrgController {
    private OrgService orgService;
    @GetMapping("/page")
    @ApiOperation(value = "分页获取单位", notes = "分页获取单位")
    public PageInfo<OrgInfo> page(PageParam<String> pageParam) {
        return orgService.page(pageParam);
    }
}
