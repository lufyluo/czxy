package com.czxy.manage.controller;

import com.czxy.manage.infrastructure.response.BaseResponse;
import com.czxy.manage.infrastructure.response.ResponseUtil;
import com.czxy.manage.model.vo.user.UserMenuInfo;
import com.czxy.manage.service.UserMenuService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api("用户菜单管理")
@RestController
@RequestMapping("/api/user/menu")
public class UserMenuController {
    @Autowired
    private UserMenuService userMenuService;

    @GetMapping("/{userId}")
    public BaseResponse<List<UserMenuInfo>> get(@PathVariable Integer userId) {
        return ResponseUtil.success(userMenuService.get(userId));
    }
}
