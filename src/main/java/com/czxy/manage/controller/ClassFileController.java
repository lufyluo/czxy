package com.czxy.manage.controller;

import com.czxy.manage.infrastructure.response.BaseResponse;
import com.czxy.manage.infrastructure.response.PageResponse;
import com.czxy.manage.infrastructure.response.ResponseUtil;
import com.czxy.manage.model.PageParam;
import com.czxy.manage.model.vo.classes.*;
import com.czxy.manage.model.vo.classes.files.ClassFileCreateInfo;
import com.czxy.manage.model.vo.classes.files.ClassFilePageParam;
import com.czxy.manage.model.vo.files.FileInfo;
import com.czxy.manage.service.ClassService;
import com.czxy.manage.service.classFile.ClassFileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@Api(tags = "班级附件管理", value = "班级附件管理")
@RequestMapping("/api/class/file")
public class ClassFileController {
    @Autowired
    private ClassFileService classFileService;

    @GetMapping("/page")
    @ApiOperation(value = "分页获取班级附件", notes = "分页获取班级附件")
    public PageResponse<FileInfo> page(ClassFilePageParam<String> pageParam) {
        return PageResponse.success(classFileService.page(pageParam));
    }

    @DeleteMapping
    @ApiOperation(value = "删除班级附件信息", notes = "删除班级附件信息")
    public BaseResponse<Boolean> delete(@RequestParam Integer classId,@RequestParam Integer fileId) {
        return ResponseUtil.success(classFileService.delete(classId,fileId));
    }

    @PostMapping
    @ApiOperation(value = "创建班级附件", notes = "创建班级附件")
    public BaseResponse<Boolean> create(@Validated @RequestBody ClassFileCreateInfo classCreateInfo) {
        return ResponseUtil.success(classFileService.create(classCreateInfo));
    }

    @PostMapping("/batch")
    @ApiOperation(value = "批量创建班级附件", notes = "批量创建班级附件",hidden = true)
    public BaseResponse<Boolean> batchInsert(@Validated @RequestBody List<Integer> fileIds,@RequestParam Integer classId) {
        return ResponseUtil.success(classFileService.batchInsert(fileIds,classId));
    }

    @PutMapping("/batch")
    @ApiOperation(value = "批量更新班级附件", notes = "批量更新班级附件",hidden = true)
    public BaseResponse<Boolean> batchUpdate(@Validated @RequestBody List<Integer> fileIds,@RequestParam Integer classId) {
        return ResponseUtil.success(classFileService.batchUpdate(classId,fileIds));
    }
}
