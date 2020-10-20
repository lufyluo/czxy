package com.czxy.manage.model.vo.teacher;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class MasterInfo {
    @ApiModelProperty(value = "班级Id",hidden = true)
    private Integer classId;
    @ApiModelProperty("用户id")
    private Integer id;
    @ApiModelProperty("用户名称")
    private String name;
}
