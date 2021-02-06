package com.czxy.manage.controller;

import com.czxy.manage.infrastructure.response.BaseResponse;
import com.czxy.manage.infrastructure.response.ResponseUtil;
import com.czxy.manage.model.vo.wechat.MediaBody;
import com.czxy.manage.model.vo.wechat.TagInfo;
import com.czxy.manage.service.WechatApiService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//@Api(hidden = true)
@RequestMapping("/api/wechat")
public class WechatApiController {
    @Autowired
    WechatApiService wechatApiService;

    @GetMapping("/tag/create")
    public BaseResponse<Integer> createTag(@RequestParam String tag) {
        return ResponseUtil.success(wechatApiService.createTag(tag));
    }

    @GetMapping("/tag/get")
    public BaseResponse<List<TagInfo>> get(@RequestParam String tag) {
        return ResponseUtil.success(wechatApiService.get(tag));
    }

    @GetMapping("/msg/preview")
    public BaseResponse<Boolean> preview(@RequestParam String openId, String msg) {
        return ResponseUtil.success(wechatApiService.preview(openId, msg));
    }

    @GetMapping("/tag/bind")
    public BaseResponse<Boolean> bind(@RequestParam Integer tagId, @RequestParam String wechatId) {
        return ResponseUtil.success(wechatApiService.bind(tagId, wechatId));
    }

    @GetMapping("/tag/clear")
    public BaseResponse<Boolean> clear(@RequestParam Integer tagId, @RequestParam String wechatId) {
        return ResponseUtil.success(wechatApiService.clear(tagId, wechatId));
    }
}
