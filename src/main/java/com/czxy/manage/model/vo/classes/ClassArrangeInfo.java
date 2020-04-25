package com.czxy.manage.model.vo.classes;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class ClassArrangeInfo {
    private Integer id;
    @ApiModelProperty("课程表名称")
    private String name;
    @ApiModelProperty("课程描述")
    private String description;
    private List<CourseDetailInfo> courseInfos;
}
