package com.czxy.manage.controller;

import com.czxy.manage.infrastructure.response.BaseResponse;
import com.czxy.manage.infrastructure.response.ResponseUtil;
import com.czxy.manage.model.vo.site.TypeInfo;
import com.czxy.manage.service.TypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Api(tags = "类别、类型",value = "类别、类型")
@RequestMapping("/api/type")
public class TypeController {
    @Autowired
    public TypeService typeService;
    @GetMapping
    @ApiOperation("根据类型关键字模糊搜索搜索，可不传")
    public BaseResponse<List<TypeInfo>> get(String key){
        return ResponseUtil.success(typeService.get(key));
    }
}
