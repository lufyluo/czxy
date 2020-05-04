package com.czxy.manage.controller;

import com.czxy.manage.infrastructure.response.BaseResponse;
import com.czxy.manage.infrastructure.response.ResponseUtil;
import com.czxy.manage.model.vo.user.UserMenuInfo;
import com.czxy.manage.model.vo.user.UserMenuSaveInfo;
import com.czxy.manage.service.UserMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "用户菜单管理",value = "用户菜单管理")
@RestController
@RequestMapping("/api/user/menu")
public class UserMenuController {
    @Autowired
    private UserMenuService userMenuService;

    @GetMapping("/{userId}")
    @ApiOperation("根据用户id获取菜单权限")
    public BaseResponse<List<UserMenuInfo>> get(@PathVariable Integer userId) {
        return ResponseUtil.success(userMenuService.get(userId));
    }

    @PutMapping
    @ApiOperation("保存用户菜单")
    public BaseResponse<Boolean> saveUserMenu(@RequestBody UserMenuSaveInfo userMenuSaveInfo) {
        return ResponseUtil.success(userMenuService.save(userMenuSaveInfo));
    }
}
