package com.czxy.manage.controller;

import com.czxy.manage.infrastructure.aop.Anonymous;
import com.czxy.manage.infrastructure.response.BaseResponse;
import com.czxy.manage.infrastructure.response.ResponseUtil;
import com.czxy.manage.model.vo.user.UserCreateInfo;
import com.czxy.manage.service.UserService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author lufy
 * @Description ...
 * @Date 20-4-15 下午7:35
 */
@Api("用户管理")
@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    UserService userService;
    @PostMapping
    public BaseResponse<Boolean> add(@RequestBody UserCreateInfo userInfo){
        return ResponseUtil.success(userService.add(userInfo));
    }
}
