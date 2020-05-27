package com.czxy.manage.controller;

import com.czxy.manage.infrastructure.response.BaseResponse;
import com.czxy.manage.infrastructure.response.PageResponse;
import com.czxy.manage.infrastructure.response.ResponseUtil;
import com.czxy.manage.model.vo.questionnaire.PaperDetailInfo;
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

    @GetMapping("/paper_answer")
    @ApiOperation("获取用户问卷详细信息")
    public BaseResponse<PaperDetailInfo> paperDetail(@RequestParam Integer userId, @RequestParam Integer paperId) {
        return ResponseUtil.success(questionnaireUserService.paperDetail(userId,paperId));
    }

    @PostMapping
    @ApiOperation("批量提交答案")
    public BaseResponse<Boolean> submit(@RequestBody List<PaperSubmitInfo> paperSubmitInfo) {
        return ResponseUtil.success(questionnaireUserService.submit(paperSubmitInfo));
    }
}
