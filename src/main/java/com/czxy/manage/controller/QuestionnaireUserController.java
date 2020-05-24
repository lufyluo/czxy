package com.czxy.manage.controller;

import com.czxy.manage.infrastructure.response.BaseResponse;
import com.czxy.manage.infrastructure.response.PageResponse;
import com.czxy.manage.infrastructure.response.ResponseUtil;
import com.czxy.manage.model.PageParam;
import com.czxy.manage.model.vo.PaperInfo;
import com.czxy.manage.model.vo.questionnaire.PaperPageInfo;
import com.czxy.manage.model.vo.questionnaire.PaperPageParam;
import com.czxy.manage.model.vo.questionnaire.PaperSubmitInfo;
import com.czxy.manage.service.QuestionnaireUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "用户问卷", value = "用户问卷")
@RestController
@RequestMapping("/api/questionnaire/user")
public class QuestionnaireUserController {
    @Autowired
    private QuestionnaireUserService questionnaireUserService;

    @GetMapping("/page")
    @ApiOperation("根据userId模糊查询试卷")
    public PageResponse<PaperPageInfo> page(PaperPageParam<String> pageParam) {
        return PageResponse.success(questionnaireUserService.page(pageParam));
    }
    @PostMapping
    @ApiOperation("批量提交答案")
    public BaseResponse<Boolean> submit(@RequestBody List<PaperSubmitInfo> paperSubmitInfo){
     return ResponseUtil.success(questionnaireUserService.submit(paperSubmitInfo));
    }
}
