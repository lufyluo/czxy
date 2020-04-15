package com.czxy.manage.controller;

import com.czxy.manage.infrastructure.response.BaseResponse;
import com.czxy.manage.infrastructure.response.PageResponse;
import com.czxy.manage.infrastructure.response.ResponseUtil;
import com.czxy.manage.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author lufy
 * @Description ...
 * @Date 20-4-15 下午7:35
 */
@Api("人员管理")
@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    UserService userService;
    @GetMapping
    @ApiOperation("get")
    public BaseResponse<Integer> get(){
        return ResponseUtil.success(userService.get());
    }

    @GetMapping("/page")
    @ApiOperation("page")
    public PageResponse<Integer> page(){
        return userService.page();
    }
}
