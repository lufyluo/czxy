package com.czxy.manage.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CompositionInfo {
    private Integer id;
    @ApiModelProperty("成分（培训对象）名称")
    private String name;
}
