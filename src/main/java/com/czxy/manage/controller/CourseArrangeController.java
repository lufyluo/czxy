package com.czxy.manage.controller;

import com.czxy.manage.infrastructure.response.BaseResponse;
import com.czxy.manage.infrastructure.response.ResponseUtil;
import com.czxy.manage.model.vo.classes.CourseArrangeAddInfo;
import com.czxy.manage.model.vo.classes.ClassArrangeInfo;
import com.czxy.manage.service.ClassCourseService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Api(tags = "班级课表",value = "班级课表" )
@RequestMapping("/api/class/course")
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
}
