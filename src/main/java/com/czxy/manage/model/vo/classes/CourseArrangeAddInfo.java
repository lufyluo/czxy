package com.czxy.manage.model.vo.classes;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class CourseArrangeAddInfo {
    private Integer id;
    @ApiModelProperty("课程表名称")
    private String name;
    @ApiModelProperty("课程描述")
    private String description;
    @ApiModelProperty("课程描述")
    List<CourseInfo> courseArrangeInfoList;
}
