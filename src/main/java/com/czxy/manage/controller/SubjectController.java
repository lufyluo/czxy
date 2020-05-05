package com.czxy.manage.controller;

import com.czxy.manage.infrastructure.response.BaseResponse;
import com.czxy.manage.infrastructure.response.PageResponse;
import com.czxy.manage.infrastructure.response.ResponseUtil;
import com.czxy.manage.model.vo.student.StudentDetailInfo;
import com.czxy.manage.model.vo.student.StudentPageParam;
import com.czxy.manage.model.vo.subject.SubjectByIdInfo;
import com.czxy.manage.model.vo.subject.SubjectDetailInfo;
import com.czxy.manage.model.vo.subject.SubjectInfo;
import com.czxy.manage.model.vo.subject.SubjectPageParam;
import com.czxy.manage.service.SubjectService;
import com.czxy.manage.service.TeacherService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

@Api(tags = "课题管理", value = "课题管理")
@RestController
@RequestMapping("/api/subject")
@Validated
public class SubjectController {
    @Autowired
    private SubjectService subjectService;

    @GetMapping("/page")
    @ApiOperation(value = "分页课题", notes = "分页获取课题")
    public PageResponse<SubjectDetailInfo> page(SubjectPageParam<String> pageParam) {
        return PageResponse.success(subjectService.page(pageParam));
    }
    @PostMapping
    @ApiOperation("新增课题")
    public BaseResponse<Boolean> add(@RequestBody SubjectInfo subjectInfo){
      return ResponseUtil.success(subjectService.add(subjectInfo));
    }
    @GetMapping("/{subjectId}")
    @ApiOperation(value = "编辑课题",notes = "根据课题ID返回课题信息")
    public BaseResponse<SubjectByIdInfo> getById(@PathVariable Integer subjectId){
        return ResponseUtil.success(subjectService.getById(subjectId));
    }
    @PutMapping
    @ApiOperation("编辑课题")
    public BaseResponse<Boolean> update(@RequestBody SubjectInfo subjectInfo){
        return ResponseUtil.success(subjectService.update(subjectInfo));
    }

}
