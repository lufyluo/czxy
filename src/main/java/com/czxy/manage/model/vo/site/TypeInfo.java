package com.czxy.manage.model.vo.site;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class TypeInfo {
    @ApiModelProperty("类型名称")
    private String name;
    @ApiModelProperty("类型id，为空则根据类型名称创建")
    private Integer id;
    @ApiModelProperty("0-类型，1-主题")
    @Min(value = 0,message = "类型错误")
    private Integer category;
}
