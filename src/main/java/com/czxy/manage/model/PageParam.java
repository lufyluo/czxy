package com.czxy.manage.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author lufy
 * @Description ...
 * @Date 19-11-18 下午3:50
 */
@Data
public class PageParam<T> {
    @ApiModelProperty("页码，默认1")
    private int pageIndex = 1;
    @ApiModelProperty("页大小，默认10")
    private int pageSize = 10;
    @ApiModelProperty("查询参数关键字")
    public T param;
}
