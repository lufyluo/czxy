package com.czxy.manage.model.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class CourseDetailEntity extends CourseEntity {
    private String teacherName;
    private String address;
    private Integer offset;
    @ApiModelProperty("课程类型 0-课程，1-点位，2-其他")
    private Integer category;
    private Long beginTime;
    private Long endTime;
}
