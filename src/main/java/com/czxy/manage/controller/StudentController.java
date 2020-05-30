package com.czxy.manage.controller;

import com.czxy.manage.infrastructure.aop.Anonymous;
import com.czxy.manage.infrastructure.response.BaseResponse;
import com.czxy.manage.infrastructure.response.PageResponse;
import com.czxy.manage.infrastructure.response.ResponseUtil;
import com.czxy.manage.model.vo.classes.ClassOrgInfo;
import com.czxy.manage.model.vo.classes.ClassPageParam;
import com.czxy.manage.model.vo.student.*;
import com.czxy.manage.service.StudentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api(tags = "学生管理",value = "学生管理")
@RequestMapping("/api/student")
public class StudentController {
    @Autowired
    private StudentService studentService;

    @GetMapping("/page")
    @ApiOperation(value = "分页获取", notes = "分页获取")
    public PageResponse<StudentDetailInfo> page(StudentPageParam<String> pageParam) {
        return PageResponse.success(studentService.page(pageParam));
    }

    @PutMapping
    @ApiOperation("一键签到")
    public BaseResponse<Boolean> sign(@RequestBody List<Integer> studentIds) {
        return ResponseUtil.success(studentService.sign(studentIds));
    }

    @DeleteMapping("/{studentIds}")
    @ApiOperation("删除学员")
    public BaseResponse<Boolean> delete(@PathVariable List<Integer> studentIds) {
        return ResponseUtil.success(studentService.delete(studentIds));
    }

    @PostMapping("/add")
    @ApiOperation("新增学员")
    public BaseResponse<Boolean> add(@Validated @RequestBody StudentAddInfo studentAddInfo) {
        return ResponseUtil.success(studentService.add(studentAddInfo));
    }

    @PostMapping("/import")
    @ApiOperation("批量导入/新增")
    public BaseResponse<Boolean> importExcel(@RequestBody List<StudentAddInfo> studentAddInfos) {
        return ResponseUtil.success(studentService.importExcel(studentAddInfos));
    }

    @PutMapping("/update")
    @ApiOperation("编辑学员")
    public BaseResponse<Boolean> update(@RequestBody StudentUpdateInfo studentUpdateInfo) {
        return ResponseUtil.success(studentService.update(studentUpdateInfo));
    }

    @PutMapping("/wechatSign")
    @ApiOperation("微信签到")
    @Anonymous
    public BaseResponse<Boolean> signByWechat(@RequestParam("phone") String phone, @RequestParam("code") String code) {
        return ResponseUtil.success(studentService.signByWechat(phone, code));
    }
    @GetMapping("/{userId}")
    @ApiOperation("获取学员信息")
    public BaseResponse<StudentClassNameInfo> get(@PathVariable Integer userId){
        return ResponseUtil.success(studentService.get(userId));
    }

}
