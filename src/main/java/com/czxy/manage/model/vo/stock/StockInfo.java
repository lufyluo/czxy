package com.czxy.manage.model.vo.stock;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class StockInfo {
    private Integer id;
    @ApiModelProperty(hidden = true)
    private String type;
    private String goodsName;
    @ApiModelProperty("单位")
    private String unit;
    @ApiModelProperty("规格")
    private String spec;
    @ApiModelProperty("消耗")
    private Integer spend;
    @ApiModelProperty("0-入库操作；1-出库操作")
    private Integer op;
    @ApiModelProperty(hidden = true)
    private Integer originId;
    private Integer classId;
    private Integer userId;
    @ApiModelProperty("本次操作后剩余数量")
    private Integer total;
    private String className;
    @ApiModelProperty("班主任名字")
    private String classMasterName;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date updatedTime;
}
