package com.czxy.manage.controller;

import com.czxy.manage.infrastructure.response.BaseResponse;
import com.czxy.manage.infrastructure.response.PageResponse;
import com.czxy.manage.infrastructure.response.ResponseUtil;
import com.czxy.manage.model.vo.StockPageParam;
import com.czxy.manage.model.vo.stock.StockOutInfo;
import com.czxy.manage.model.vo.stock.StockInfo;
import com.czxy.manage.model.vo.stock.StockTotalInfo;
import com.czxy.manage.service.StockService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/stock")
@Api(tags = "后勤管理", value = "后勤管理")
public class StockController {
    @Autowired
    private StockService stockService;

    @GetMapping("/page")
    @ApiOperation("后勤分页")
    public PageResponse<StockInfo> page(StockPageParam<String> pageParam) {
        return PageResponse.success(stockService.page(pageParam));
    }

    @PostMapping("/out")
    @ApiOperation("出库")
    public BaseResponse<Boolean> out(@RequestBody StockOutInfo stockOutInfo) {
        return ResponseUtil.success(stockService.out(stockOutInfo));
    }
    @PostMapping("/in")
    @ApiOperation("入库")
    public BaseResponse<Boolean> in(@RequestBody StockOutInfo stockOutInfo){
        return ResponseUtil.success(stockService.in(stockOutInfo));
    }
    @GetMapping("/total")
    @ApiOperation("结存")
    public BaseResponse<Integer> total(StockTotalInfo stockTotalInfo){
        return ResponseUtil.success(stockService.total(stockTotalInfo));
    }
}
