package com.czxy.manage.controller;

import com.czxy.manage.infrastructure.response.BaseResponse;
import com.czxy.manage.infrastructure.response.ResponseUtil;
import com.czxy.manage.model.vo.site.SiteAddInfo;
import com.czxy.manage.model.vo.student.StudentDetailInfo;
import com.czxy.manage.model.vo.student.StudentPageParam;
import com.czxy.manage.model.vo.teacher.*;
import com.czxy.manage.service.TeacherService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @PostMapping
    @ApiOperation("新增教师")
    public BaseResponse<Boolean> add(@RequestBody TeacherInfo teacherInfo){
        return ResponseUtil.success(teacherService.add(teacherInfo));
    }
    @DeleteMapping
    @ApiOperation("批量删除教师")
    public BaseResponse<Boolean> delete(@RequestBody List<Integer> teacherIds){
        return ResponseUtil.success(teacherService.delete(teacherIds));
    }
    @PutMapping
    @ApiOperation("编辑教师")
    public BaseResponse<Boolean> update(@RequestBody TeacherUpdateInfo teacherUpdateInfo){
        return ResponseUtil.success(teacherService.update(teacherUpdateInfo));
    }
    @GetMapping
    @ApiOperation("教师详情")
    public BaseResponse<TeacherInformationInfo> query(@RequestParam Integer teacherId){
        return ResponseUtil.success(teacherService.query(teacherId));
    }
}
