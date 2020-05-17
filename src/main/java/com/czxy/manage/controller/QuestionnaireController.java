package com.czxy.manage.controller;

import com.czxy.manage.infrastructure.response.BaseResponse;
import com.czxy.manage.infrastructure.response.PageResponse;
import com.czxy.manage.infrastructure.response.ResponseUtil;
import com.czxy.manage.model.PageParam;
import com.czxy.manage.model.vo.PaperInfo;
import com.czxy.manage.model.vo.questionnaire.PaperCreateInfo;
import com.czxy.manage.service.QuestionnaireService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "问卷-试卷管理", value = "试卷管理")
@RestController
@RequestMapping("/api/questionnaire")
public class QuestionnaireController {
    @Autowired
    private QuestionnaireService questionnaireService;

    @GetMapping("/page")
    @ApiOperation("分页获取")
    public PageResponse<PaperInfo> page(PageParam<String> pageParam) {
        return PageResponse.success(questionnaireService.page(pageParam));
    }

    @PutMapping
    @ApiOperation("编辑")
    public BaseResponse<Boolean> update(@RequestBody PaperCreateInfo paperInfo) {
        return ResponseUtil.success(questionnaireService.update(paperInfo));
    }

    @PostMapping
    @ApiOperation("添加")
    public BaseResponse<Boolean> add(@RequestBody PaperCreateInfo paperInfo) {
        return ResponseUtil.success(questionnaireService.add(paperInfo));
    }

    @DeleteMapping("/{paperIds}")
    @ApiOperation("批量删除")
    public BaseResponse<Boolean> delete(@PathVariable List<Integer> paperIds) {
        return ResponseUtil.success(questionnaireService.delete(paperIds));
    }
}
