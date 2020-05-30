package com.czxy.manage.controller;

import com.czxy.manage.infrastructure.response.BaseResponse;
import com.czxy.manage.infrastructure.response.ResponseUtil;
import com.czxy.manage.service.FileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
    @ResponseBody
    public BaseResponse<Boolean> upload(@RequestParam(value="file", required=false)MultipartFile file) throws Exception {
        return ResponseUtil.success(fileService.upload(file));
    }
}
