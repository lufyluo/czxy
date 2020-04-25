package com.czxy.manage.model.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class ClassFeeEntity {
    private Integer feeId;
    private String type;
    private String name;
    private String detail;
    private Integer pay;
}
