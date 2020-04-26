package com.czxy.manage.controller;

import com.czxy.manage.model.PageParam;
import com.czxy.manage.model.vo.classes.ClassInformationInfo;
import com.czxy.manage.model.vo.classes.ClassOrgInfo;
import com.czxy.manage.model.vo.classes.ClassStudentInfo;
import com.czxy.manage.service.ClassService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


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

    @GetMapping("/query")
    @ApiOperation(value = "查看班级基础信息", notes = "传入ID为class id")
    public ClassInformationInfo query(Integer id) {
        return classService.query(id);
    }

    @GetMapping("/pageStudent")
    @ApiOperation(value = "查看班级学员信息", notes = "传入ID为class id")
    public PageInfo<ClassStudentInfo> pageStudent(PageParam<Integer> pageParam){
        return classService.pageStudent(pageParam);
    }
    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除班级信息", notes = "传入id的为class表的id")
    public Boolean delete(@PathVariable Integer id) {
        return classService.delete(id);
    }


}
