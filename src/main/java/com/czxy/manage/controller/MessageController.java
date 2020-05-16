package com.czxy.manage.controller;

import com.czxy.manage.infrastructure.response.BaseResponse;
import com.czxy.manage.model.vo.SendInfo;
import com.czxy.manage.service.MessageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api("信息发布")
@RequestMapping("/api/message")
public class MessageController {
    @Autowired
    private MessageService messageService;

    @PostMapping
    @ApiOperation("消息推送")
    public BaseResponse<Boolean> send(@RequestBody SendInfo sendInfo) {
        return null;
    }
}
