package com.czxy.manage.controller;

import com.czxy.manage.infrastructure.response.BaseResponse;
import com.czxy.manage.infrastructure.response.ResponseUtil;
import com.czxy.manage.model.PageParam;
import com.czxy.manage.model.vo.classes.ClassOrgInfo;
import com.czxy.manage.model.vo.student.StudentDetailInfo;
import com.czxy.manage.model.vo.student.StudentPageParam;
import com.czxy.manage.service.StudentService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api("学生管理")
@RequestMapping("/api/student/")
public class StudentController {
    @Autowired
    private StudentService studentService;
    @GetMapping("/page")
    @ApiOperation(value = "分页获取", notes = "分页获取班级")
    public PageInfo<StudentDetailInfo> page(StudentPageParam<String> pageParam) {
        return studentService.page(pageParam);
    }
    @PutMapping
    @ApiOperation("一键签到")
    public BaseResponse<Boolean> sign(@RequestBody List<Integer> studentIds ){
           return ResponseUtil.success(studentService.sign(studentIds));
    }
}
