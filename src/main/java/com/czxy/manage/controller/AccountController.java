package com.czxy.manage.controller;

import com.czxy.manage.infrastructure.response.BaseResponse;
import com.czxy.manage.infrastructure.response.ResponseUtil;
import com.czxy.manage.model.vo.user.AccountInfo;
import com.czxy.manage.model.vo.user.ChangePwdInfo;
import com.czxy.manage.service.AccountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;

/**
 * @Author lufy
 * @Description ...
 * @Date 20-4-16 下午8:57
 */
@RestController
@Api("账号操作")
@RequestMapping("/api/account")
public class AccountController {
    @Autowired
    private AccountService accountService;
    @PostMapping("/login")
    @ApiOperation(value = "账号密码登录", notes = "登录")
    public BaseResponse<String> login(@RequestBody AccountInfo accountInfo, @RequestHeader String timestamp) {
        return ResponseUtil.success(accountService.login(accountInfo, timestamp));
    }

    @PutMapping
    @ApiOperation(value = "修改密码", notes = "登录")
    public BaseResponse<Boolean> changePassword(@RequestBody ChangePwdInfo changePwdInfo,@RequestHeader String token) {
        return ResponseUtil.success(accountService.changePassword(changePwdInfo,token));
    }

    @DeleteMapping("/{accountId}")
    @ApiOperation(value = "账号密码登录", notes = "登录")
    public BaseResponse<Boolean> delete(@PathVariable @Min(value = 1,message = "参数错误") Integer accountId) {
        return ResponseUtil.success(accountService.delete(accountId));
    }
}
