package com.czxy.manage.controller;

import com.czxy.manage.infrastructure.response.BaseResponse;
import com.czxy.manage.infrastructure.response.ResponseUtil;
import com.czxy.manage.model.vo.IndexInfo;
import com.czxy.manage.model.vo.NumbersInfo;
import com.czxy.manage.model.vo.index.RankInfo;
import com.czxy.manage.model.vo.index.TrendInfo;
import com.czxy.manage.service.IndexService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@Api(tags = "首页", value = "首页")
@RequestMapping("/api/index")
public class IndexController {
    @Autowired
    private IndexService indexService;

    @GetMapping("/{userId}")
    @ApiOperation("获取首页轮播图url")
    public BaseResponse<IndexInfo> get(@PathVariable Integer userId) {
        return ResponseUtil.success(indexService.get(userId));
    }

    @GetMapping("/count")
    @ApiOperation("获取首页第一栏各人数的数量")
    public BaseResponse<NumbersInfo> getNumbers() {
        return ResponseUtil.success(indexService.getNumbers());
    }

    @GetMapping("/rank/teacher")
    @ApiOperation("获取教师上课次数排名")
    public BaseResponse<List<RankInfo>> getTeacherRank() {
        return ResponseUtil.success(indexService.getTeacherRank());
    }

    @GetMapping("/rank/site")
    @ApiOperation("获取点位上课次数排名")
    public BaseResponse<List<RankInfo>> getSiteRank() {
        return ResponseUtil.success(indexService.getSiteRank());
    }

    @GetMapping("/trend/class")
    @ApiOperation("获取班级数量增长趋势")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "beginTime",value = "开始时间：{yyyy-MM-dd}",example = "yyyy-MM-dd",dataType="date"),
            @ApiImplicitParam(name = "endTime", value = "结束时间：{yyyy-MM-dd}",example = "yyyy-MM-dd",dataType="date")
    })
    public BaseResponse<List<RankInfo>> getClassTrend(@RequestParam @DateTimeFormat(pattern ="yyyy-MM-dd") Date beginTime,
                                                      @RequestParam @DateTimeFormat(pattern ="yyyy-MM-dd") Date endTime) {
        return ResponseUtil.success(indexService.getClassTrend(beginTime, endTime));
    }

    @GetMapping("/trend/student")
    @ApiOperation("获取学生数量增长趋势")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "beginTime", value = "开始时间：{yyyy-MM-dd}",example = "yyyy-MM-dd",dataType="date"),
            @ApiImplicitParam(name = "endTime", value = "结束时间：{yyyy-MM-dd}",example = "yyyy-MM-dd",dataType="date")
    })
    public BaseResponse<List<RankInfo>> getStudentTrend(@RequestParam @DateTimeFormat(pattern ="yyyy-MM-dd")Date beginTime,
                                                        @RequestParam @DateTimeFormat(pattern ="yyyy-MM-dd")Date endTime) {
        return ResponseUtil.success(indexService.getStudentTrend(beginTime, endTime));
    }
}
