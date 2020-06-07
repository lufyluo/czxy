package com.czxy.manage.controller;

import com.czxy.manage.infrastructure.aop.Anonymous;
import com.czxy.manage.infrastructure.aop.FileAnonymous;
import com.czxy.manage.infrastructure.response.BaseResponse;
import com.czxy.manage.infrastructure.response.PageResponse;
import com.czxy.manage.infrastructure.response.ResponseUtil;
import com.czxy.manage.model.vo.site.SiteAddInfo;
import com.czxy.manage.model.vo.student.StudentDetailInfo;
import com.czxy.manage.model.vo.student.StudentPageParam;
import com.czxy.manage.model.vo.teacher.*;
import com.czxy.manage.service.TeacherService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@Api(tags = "师资管理",value = "师资管理")
@RequestMapping("/api/teacher")
public class TeacherController {
    @Autowired
    private TeacherService teacherService;
    @GetMapping("/page")
    @ApiOperation(value = "分页获取", notes = "根据教师姓名、星级、职称分页获取，参数选填")
    public PageResponse<TeacherDetailInfo> page(TeacherPageParam<String> pageParam) {
        return PageResponse.success(teacherService.page(pageParam));
    }
    @PostMapping
    @ApiOperation("新增教师")
    public BaseResponse<Boolean> add(@RequestBody TeacherInfo teacherInfo){
        return ResponseUtil.success(teacherService.add(teacherInfo));
    }
    @DeleteMapping("/{teacherIds}")
    @ApiOperation("批量删除教师")
    public BaseResponse<Boolean> delete(@PathVariable List<Integer> teacherIds){
        return ResponseUtil.success(teacherService.delete(teacherIds));
    }
    @PutMapping
    @ApiOperation("编辑教师")
    public BaseResponse<Boolean> update(@RequestBody TeacherUpdateInfo teacherUpdateInfo){
        return ResponseUtil.success(teacherService.update(teacherUpdateInfo));
    }
    @GetMapping
    @ApiOperation("教师详情")
    public BaseResponse<TeacherInformationInfo> query(@RequestParam Integer teacherId){
        return ResponseUtil.success(teacherService.query(teacherId));
    }
    @GetMapping("/wechatGet")
    @ApiOperation("师资推荐")
    @Anonymous
    public BaseResponse<List<TeacherWechatInfo>> get(){
        return ResponseUtil.success(teacherService.get());
    }

    @ApiOperation("导入")
    @RequestMapping(path = "/import/{system}", method = RequestMethod.POST)
    @ApiImplicitParams({@ApiImplicitParam(name = "system",value = "0-党校系统，1-领导干部系统，2-高校系统")})
    @FileAnonymous
    public BaseResponse<List<ImportTeacherInfo>> batchImport(@PathVariable("system") Integer system,
                                                             @RequestParam(value = "file") MultipartFile file){
        return ResponseUtil.success(teacherService.batchImport(system,file));
    }
}
