package com.czxy.manage.controller;

import com.czxy.manage.infrastructure.aop.Anonymous;
import com.czxy.manage.infrastructure.aop.FileAnonymous;
import com.czxy.manage.infrastructure.response.BaseResponse;
import com.czxy.manage.infrastructure.response.ResponseUtil;
import com.czxy.manage.model.vo.files.FileInfo;
import com.czxy.manage.service.FileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@Api(tags = "文件上传下载",value = "文件上传下载")
@RequestMapping("/api/file")
public class FileController {

    @Autowired
    private FileService fileService;


    @ApiOperation("文件上传")
    @PostMapping("/upload/oss")
    @FileAnonymous
    @Anonymous
    public BaseResponse<FileInfo> upload(@RequestParam("file") MultipartFile file) {
        return ResponseUtil.success(fileService.upload(file));
    }
}
