package com.czxy.manage.controller;

import com.czxy.manage.infrastructure.response.BaseResponse;
import com.czxy.manage.infrastructure.response.PageResponse;
import com.czxy.manage.infrastructure.response.ResponseUtil;
import com.czxy.manage.model.PageParam;
import com.czxy.manage.model.vo.questionnaire.stem.StemInfo;
import com.czxy.manage.service.QuestionnaireStemService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import java.util.List;

@Api(tags = "问卷-题目", value = "试卷-题目")
@RestController
@RequestMapping("/api/questionnaire/stem")
public class QuestionnaireStemController {
    @Autowired
    private QuestionnaireStemService questionnaireStemService;

    @GetMapping("/page")
    @ApiOperation("分页获取")
    public PageResponse<StemInfo> page(PageParam<String> pageParam) {
        return PageResponse.success(questionnaireStemService.page(pageParam));
    }

    @GetMapping("/{paperId}}")
    @ApiOperation("分页获取")
    public BaseResponse<List<StemInfo>> get(@PathVariable @Min(value = 1) Integer paperId) {
        return ResponseUtil.success(questionnaireStemService.get(paperId));
    }

    @PutMapping
    @ApiOperation("编辑")
    public BaseResponse<Boolean> update(@RequestBody StemInfo stemUpdateInfo) {
        return ResponseUtil.success(questionnaireStemService.update(stemUpdateInfo));
    }

    @PostMapping
    @ApiOperation("添加")
    public BaseResponse<Integer> add(@RequestBody StemInfo stemCreateInfo) {
        return ResponseUtil.success(questionnaireStemService.add(stemCreateInfo));
    }

    @DeleteMapping("/{stemIds}")
    @ApiOperation("批量删除")
    public BaseResponse<Boolean> delete(@PathVariable List<Integer> stemIds) {
        return ResponseUtil.success(questionnaireStemService.delete(stemIds));
    }
}
