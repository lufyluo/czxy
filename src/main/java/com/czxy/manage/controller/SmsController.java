package com.czxy.manage.controller;

import com.aliyuncs.CommonResponse;
import com.czxy.manage.infrastructure.response.BaseResponse;
import com.czxy.manage.infrastructure.response.ResponseUtil;
import com.czxy.manage.infrastructure.util.aliyun.AliyunBirthdaySmsConfig;
import com.czxy.manage.infrastructure.util.aliyun.SmsUtil;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api
@RequestMapping("/api/sms")
public class SmsController {
    @Autowired
    private SmsUtil smsUtil;

    @Autowired
    private AliyunBirthdaySmsConfig config;
    @GetMapping("/send")
    public BaseResponse<CommonResponse> sendMsg(String code, String msg) {
        CommonResponse commonResponse = smsUtil.sendTemplate(config.getTemplate(), config.getSign(),"17381842834",msg);
        return ResponseUtil.success(commonResponse);
    }
}
