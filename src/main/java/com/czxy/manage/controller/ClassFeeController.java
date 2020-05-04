package com.czxy.manage.controller;

import com.czxy.manage.infrastructure.response.BaseResponse;
import com.czxy.manage.infrastructure.response.ResponseUtil;
import com.czxy.manage.model.vo.classes.ClassFeeDetailInfo;
import com.czxy.manage.service.ClassFeeService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@Api(tags = "班级费用",value = "班级费用")
@RequestMapping("/api/class/fee_detail")
public class ClassFeeController {
    @Autowired
    private ClassFeeService classFeeService;

    @GetMapping("/{classId}")
    public BaseResponse<List<ClassFeeDetailInfo>> get(@PathVariable Integer classId){
        return ResponseUtil.success(classFeeService.get(classId)) ;
    }
}
