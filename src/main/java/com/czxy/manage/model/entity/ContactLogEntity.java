package com.czxy.manage.model.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
public class ContactLogEntity extends BaseEntity{
    private Integer id;
    private Integer planId;
    @ApiModelProperty("内容")
    private String content;
}
