package com.czxy.manage.controller;

import com.czxy.manage.infrastructure.response.BaseResponse;
import com.czxy.manage.infrastructure.response.PageResponse;
import com.czxy.manage.infrastructure.response.ResponseUtil;
import com.czxy.manage.model.PageParam;
import com.czxy.manage.model.vo.arrange.ArrangeInfo;
import com.czxy.manage.model.vo.classes.CourseArrangeAddInfo;
import com.czxy.manage.model.vo.classes.ClassArrangeInfo;
import com.czxy.manage.service.ClassCourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api(tags = "班级课表",value = "班级课表" )
@RequestMapping("/api/class_arrange")
public class CourseArrangeController {
    @Autowired
    ClassCourseService classCourseService;
    @GetMapping("/{classId}")
    public BaseResponse<ClassArrangeInfo> get(@PathVariable Integer classId){
        return ResponseUtil.success(classCourseService.get(classId));
    }

    @PostMapping
    public BaseResponse<Boolean> add(@RequestBody CourseArrangeAddInfo courseArrangeAddInfo){
        return ResponseUtil.success(classCourseService.add(courseArrangeAddInfo));
    }
    @GetMapping("/page")
    @ApiOperation("分页获取课表")
    public PageResponse<ArrangeInfo> page(PageParam<String> pageParam){
        return PageResponse.success(classCourseService.page(pageParam));
    }
    @DeleteMapping("/{arrangeIds}")
    @ApiOperation("批量删除课表信息")
    public BaseResponse<Boolean> delete(@PathVariable List<Integer> arrangeIds){
        return ResponseUtil.success(classCourseService.delete(arrangeIds));
    }
}
