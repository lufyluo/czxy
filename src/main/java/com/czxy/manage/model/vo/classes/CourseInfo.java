package com.czxy.manage.model.vo.classes;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class CourseInfo {
    private Integer id;
    @ApiModelProperty("课题")
    private String name;
    private String description;
    @ApiModelProperty("0-党政综合；1-社会管理；2-农业农村；3-城建规划；4-经济与产业；5-能力素质提升；20-其他")
    private Integer type;
    private Integer teacherId;
}
