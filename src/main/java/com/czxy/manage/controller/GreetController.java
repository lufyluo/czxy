package com.czxy.manage.controller;

import com.czxy.manage.infrastructure.response.BaseResponse;
import com.czxy.manage.infrastructure.response.PageResponse;
import com.czxy.manage.infrastructure.response.ResponseUtil;
import com.czxy.manage.model.PageParam;
import com.czxy.manage.model.vo.GreetInfo;
import com.czxy.manage.model.vo.GreetPageParam;
import com.czxy.manage.service.GreetService;
import com.sun.org.apache.xpath.internal.operations.Bool;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/greet")
@Api(tags = "问候语配置", value = "问候语配置")
public class GreetController {
    @Autowired
    private GreetService greetService;
    @GetMapping
    @ApiOperation("问候语分页")
    public PageResponse<GreetInfo> page(GreetPageParam<String> pageParam){
        return PageResponse.success(greetService.page(pageParam));
    }
    @PostMapping
    @ApiOperation("新增问候语")
    public BaseResponse<Boolean> add(@RequestBody GreetInfo greetInfo){
      return ResponseUtil.success(greetService.add(greetInfo));
    }
    @PutMapping
    @ApiOperation("编辑问候语")
    public BaseResponse<Boolean> update(@RequestBody GreetInfo greetInfo){
     return ResponseUtil.success(greetService.update(greetInfo));
    }
    @DeleteMapping("/{ids}")
    @ApiOperation("批量删除问候语")
    public BaseResponse<Boolean> delete(@PathVariable List<Integer> ids){
        return ResponseUtil.success(greetService.delete(ids));
    }
}
