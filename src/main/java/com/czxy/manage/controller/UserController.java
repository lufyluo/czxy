package com.czxy.manage.controller;

import com.czxy.manage.infrastructure.response.BaseResponse;
import com.czxy.manage.infrastructure.response.ResponseUtil;
import com.czxy.manage.model.vo.user.UserCreateInfo;
import com.czxy.manage.model.vo.user.UserInfo;
import com.czxy.manage.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/token")
    @ApiOperation("根据token获取用户信息")
    public UserInfo query(@RequestHeader String token) {
        return userService.query(token);
    }

    @PostMapping("/add")
    @ApiOperation(value = "添加用户", notes = "工作单位如果选择已经存在的则必须传orgId")
    public BaseResponse<Boolean> add(@RequestBody UserCreateInfo userInfo) {
        return ResponseUtil.success(userService.add(userInfo));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除用户信息", notes = "传入id的为userId")
    public BaseResponse<Boolean> delete(@PathVariable Integer id) {
        return ResponseUtil.success(userService.delete(id));
    }

    @PutMapping
    @ApiOperation(value = "编辑用户信息", notes = "工作单位如果选择已经存在的则必须传入orgId")
    public BaseResponse<Boolean> update(@RequestBody UserCreateInfo userCreateInfo) {
        return ResponseUtil.success(userService.update(userCreateInfo));
    }
}
