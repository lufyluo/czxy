package com.czxy.manage.controller;

import com.czxy.manage.model.PageParam;
import com.czxy.manage.model.vo.classes.ClassOrgInfo;
import com.czxy.manage.model.vo.user.UserAccountInfo;
import com.czxy.manage.service.ClassService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api("班级管理")
@RequestMapping("/api/class")
public class ClassController {
    @Autowired
    private ClassService classService;
    @GetMapping("/page")
    @ApiOperation(value = "分页获取班级", notes = "分页获取班级")
    public PageInfo<ClassOrgInfo> page(PageParam<String> pageParam) {
        return classService.page(pageParam);
    }


}
