package com.czxy.manage.controller;

import com.czxy.manage.infrastructure.response.BaseResponse;
import com.czxy.manage.infrastructure.response.PageResponse;
import com.czxy.manage.infrastructure.response.ResponseUtil;
import com.czxy.manage.model.vo.student.StudentAddInfo;
import com.czxy.manage.model.vo.student.StudentDetailInfo;
import com.czxy.manage.model.vo.student.StudentPageParam;
import com.czxy.manage.model.vo.student.StudentUpdateInfo;
import com.czxy.manage.service.StudentService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api(tags = "学生管理",value = "学生管理")
@RequestMapping("/api/student")
public class StudentController {
    @Autowired
    private StudentService studentService;

    @GetMapping("/page")
    @ApiOperation(value = "分页获取", notes = "分页获取班级")
    public PageResponse<StudentDetailInfo> page(StudentPageParam<String> pageParam) {
        return PageResponse.success(studentService.page(pageParam));
    }

    @PutMapping
    @ApiOperation("一键签到")
    public BaseResponse<Boolean> sign(@RequestBody List<Integer> studentIds) {
        return ResponseUtil.success(studentService.sign(studentIds));
    }

    @DeleteMapping
    @ApiOperation("删除学员")
    public BaseResponse<Boolean> delete(@RequestBody List<Integer> studentIds) {
        return ResponseUtil.success(studentService.delete(studentIds));
    }

    @PostMapping("/add")
    @ApiOperation("新增学员")
    public BaseResponse<Boolean> add(@RequestBody StudentAddInfo studentAddInfo) {
        return ResponseUtil.success(studentService.add(studentAddInfo));
    }

    @PostMapping("/import")
    @ApiOperation("新增学员")
    public BaseResponse<Boolean> importExcel(@RequestBody List<StudentAddInfo> studentAddInfos) {
        return ResponseUtil.success(studentService.batchInsert(studentAddInfos));
    }

    @PutMapping("/update")
    @ApiOperation("编辑学员")
    public BaseResponse<Boolean> update(@RequestBody StudentUpdateInfo studentUpdateInfo) {
        return ResponseUtil.success(studentService.update(studentUpdateInfo));
    }
}
