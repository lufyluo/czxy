package com.czxy.manage.model.vo.stock;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class StockOutInfo {
    @ApiModelProperty(hidden = true)
    private Integer id;
    private String goodsName;
    @ApiModelProperty("单位")
    private String unit;
    @ApiModelProperty("规格")
    private String spec;
    @ApiModelProperty("消耗")
    private Integer spend;
    @ApiModelProperty("0-入库操作；1-出库操作")
    private Integer op;
    private Integer classId;
    @ApiModelProperty("本次操作后剩余数量")
    private Integer total;
}
