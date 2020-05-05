package com.czxy.manage.controller;

import com.czxy.manage.infrastructure.response.BaseResponse;
import com.czxy.manage.infrastructure.response.PageResponse;
import com.czxy.manage.infrastructure.response.ResponseUtil;
import com.czxy.manage.model.PageParam;
import com.czxy.manage.model.vo.classes.ClassOrgInfo;
import com.czxy.manage.model.vo.user.UserCreateInfo;
import com.czxy.manage.model.vo.user.UserInfo;
import com.czxy.manage.service.OrgService;
import com.czxy.manage.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author lufy
 * @Description ...
 * @Date 20-4-15 下午7:35
 */
@Api(tags = "用户管理",value = "用户管理")
@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping("/token")
    @ApiOperation("根据token获取用户信息")
    public BaseResponse<UserInfo> query(@RequestHeader String token) {
        return ResponseUtil.success(userService.query(token));
    }

    @PostMapping("/add")
    @ApiOperation(value = "添加用户", notes = "工作单位如果选择已经存在的则必须传orgId")
    public BaseResponse<Boolean> add(@RequestBody UserCreateInfo userInfo) {
        return ResponseUtil.success(userService.add(userInfo));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除用户信息", notes = "传入id的为userId")
    public BaseResponse<Boolean> delete(@PathVariable List<Integer> id) {
        return ResponseUtil.success(userService.delete(id));
    }

    @PutMapping
    @ApiOperation(value = "编辑用户信息", notes = "工作单位如果选择已经存在的则必须传入orgId")
    public BaseResponse<Boolean> update(@RequestBody UserCreateInfo userCreateInfo) {
        return ResponseUtil.success(userService.update(userCreateInfo));
    }
    @GetMapping("/page")
    @ApiOperation(value = "分页获取班主任", notes = "分页获取班主任")
    public PageResponse<UserInfo> page(PageParam<String> pageParam) {
        return PageResponse.success(userService.page(pageParam));
    }
}
