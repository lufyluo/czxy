package com.czxy.manage.model.vo.classes;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class CourseDetailInfo extends CourseInfo {
    private String teacherName;
    private String address;
    private Integer offset;
    @ApiModelProperty("课程类型 0-课程，1-点位，2-其他")
    private Integer category;
    @JsonFormat(pattern = "yyyy.MM.dd hh:mm", timezone = "GMT+8")
    private Date beginTime;
    @JsonFormat(pattern = "yyyy.MM.dd hh:mm", timezone = "GMT+8")
    private Date endTime;
}
