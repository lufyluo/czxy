package com.czxy.manage.controller;

import com.czxy.manage.infrastructure.response.BaseResponse;
import com.czxy.manage.infrastructure.response.PageResponse;
import com.czxy.manage.infrastructure.response.ResponseUtil;
import com.czxy.manage.model.PageParam;
import com.czxy.manage.model.vo.SendInfo;
import com.czxy.manage.model.vo.message.MessageInfo;
import com.czxy.manage.model.vo.message.UserPageParam;
import com.czxy.manage.service.MessageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Api(tags = "信息发布", value = "信息发布")
@RequestMapping("/api/message")
public class MessageController {
    @Autowired
    private MessageService messageService;

    @PostMapping
    @ApiOperation("消息推送")
    public BaseResponse<Boolean> send(@RequestBody SendInfo sendInfo) {
        return ResponseUtil.success(messageService.send(sendInfo));
    }

    @GetMapping("/page")
    @ApiOperation("分页获取用户消息")
    public PageResponse<MessageInfo> get(UserPageParam<String> pageParam) {
        return PageResponse.success(messageService.get(pageParam));
    }
    @GetMapping("/{userId}")
    @ApiOperation("获取最新消息")
    public BaseResponse<MessageInfo> getNews(@PathVariable Integer userId){
        return ResponseUtil.success(messageService.getNews(userId));
    }
}
