package com.czxy.manage.controller;

import com.czxy.manage.infrastructure.response.BaseResponse;
import com.czxy.manage.infrastructure.response.ResponseUtil;
import com.czxy.manage.model.vo.chat.ChatContentCreateInfo;
import com.czxy.manage.model.vo.chat.ChatContentInfo;
import com.czxy.manage.service.ChatroomService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api(tags = "公众号班级互动区", value = "公众号班级互动区")
@RequestMapping("/api/class/chat")
public class ChatroomController {
    @Autowired
    private ChatroomService chatroomService;

    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "classId", value = "班级id"),
                    @ApiImplicitParam(name = "lastId", value = "最后一条数据的id"),
                    @ApiImplicitParam(name = "step", value = "步长，获取以后的数据长度，类似分页pageSize")
            }
    )
    @GetMapping
    @ApiOperation("获取内容")
    public BaseResponse<List<ChatContentInfo>> query(Integer classId, Integer lastId, Integer step) {
        return ResponseUtil.success(chatroomService.query(classId, lastId, step));
    }

    @PostMapping
    public BaseResponse<Boolean> post(@RequestBody @Validated ChatContentCreateInfo chatContentCreateInfo) {
        return ResponseUtil.success(chatroomService.post(chatContentCreateInfo));

    }
}
