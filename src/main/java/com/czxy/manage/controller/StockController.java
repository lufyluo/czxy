package com.czxy.manage.controller;

import com.czxy.manage.infrastructure.response.PageResponse;
import com.czxy.manage.model.vo.stock.StockInfo;
import com.czxy.manage.service.StockService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/stock")
@Api(tags = "后勤管理", value = "后勤管理")
public class StockController {
    @Autowired
    private StockService stockService;

//    public PageResponse<StockInfo> page() {
//        return PageResponse.success(stockService.page());
//    }
}
