package com.czxy.manage.controller;

import com.czxy.manage.model.vo.student.StudentDetailInfo;
import com.czxy.manage.model.vo.student.StudentPageParam;
import com.czxy.manage.model.vo.teacher.TeacherDetailInfo;
import com.czxy.manage.model.vo.teacher.TeacherPageParam;
import com.czxy.manage.service.TeacherService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api("师资管理")
@RequestMapping("/api/teacher/")
public class TeacherController {
    @Autowired
    private TeacherService teacherService;
    @GetMapping("/page")
    @ApiOperation(value = "分页获取", notes = "分页获取班级")
    public PageInfo<TeacherDetailInfo> page(TeacherPageParam<String> pageParam) {
        return teacherService.page(pageParam);
    }
}
