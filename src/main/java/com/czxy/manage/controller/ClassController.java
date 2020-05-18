package com.czxy.manage.controller;

import com.czxy.manage.infrastructure.response.BaseResponse;
import com.czxy.manage.infrastructure.response.PageResponse;
import com.czxy.manage.infrastructure.response.ResponseUtil;
import com.czxy.manage.model.PageParam;
import com.czxy.manage.model.vo.classes.*;
import com.czxy.manage.model.vo.student.StudentPageParam;
import com.czxy.manage.service.ClassService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@Api(tags = "班级管理",value = "班级管理" )
@RequestMapping("/api/class")
public class ClassController {
    @Autowired
    private ClassService classService;

    @GetMapping("/page")
    @ApiOperation(value = "分页获取班级", notes = "分页获取班级")
    public PageResponse<ClassOrgInfo> page(ClassPageParam<String> pageParam) {
        return PageResponse.success(classService.page(pageParam));
    }

    @GetMapping("/query")
    @ApiOperation(value = "查看班级基础信息", notes = "传入ID为class id")
    public BaseResponse<ClassInformationInfo> query(Integer id) {
        return ResponseUtil.success(classService.query(id));
    }

    @GetMapping("/pageStudent")
    @ApiOperation(value = "查看班级学员信息", notes = "传入ID为class id")
    public PageResponse<ClassStudentInfo> pageStudent(PageParam<String> pageParam) {
        return PageResponse.success(classService.pageStudent(pageParam));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除班级信息", notes = "传入id的为class表的id")
    public BaseResponse<Boolean> delete(@PathVariable List<Integer> id) {
        return ResponseUtil.success(classService.delete(id));
    }

    @PostMapping
    @ApiOperation(value = "创建班级", notes = "创建班级")
    public BaseResponse<Boolean> create(@RequestBody ClassCreateInfo classCreateInfo) {
        return ResponseUtil.success(classService.create(classCreateInfo));
    }
    @PutMapping
    @ApiOperation(value = "编辑班级", notes = "编辑班级")
    public BaseResponse<Boolean> update(@RequestBody ClassUpdateInfo classUpdateInfo) {
        return ResponseUtil.success(classService.update(classUpdateInfo));
    }
    @PutMapping("/studentClass")
    @ApiOperation("根据classID设置学生班级信息")
    public BaseResponse<Boolean> updateStudentClass(@RequestBody StudentClassInfo studentClassInfo){
        return ResponseUtil.success(classService.updateStudentClass(studentClassInfo));
    }
}
