package com.czxy.manage.controller;

import com.czxy.manage.infrastructure.response.BaseResponse;
import com.czxy.manage.infrastructure.response.ResponseUtil;
import com.czxy.manage.model.vo.AddressInfo;
import com.czxy.manage.service.AddressService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Api("地址")
@RequestMapping("/api/address")
public class AddressController {
    @Autowired
    private AddressService addressService;

    @GetMapping("/province/{parentId}")
    @ApiOperation("获取省")
    public BaseResponse<List<AddressInfo>> getProvince(@PathVariable Integer parentId) {
        return ResponseUtil.success(addressService.get(parentId, 1));
    }

    @GetMapping("/city/{parentId}")
    @ApiOperation("获取市")
    public BaseResponse<List<AddressInfo>> getCity(@PathVariable Integer parentId) {
        return ResponseUtil.success(addressService.get(parentId, 2));
    }

    @GetMapping("/county/{parentId}")
    @ApiOperation("获取县、区")
    public BaseResponse<List<AddressInfo>> getCounty(@PathVariable Integer parentId) {
        return ResponseUtil.success(addressService.get(parentId, 3));
    }
}
