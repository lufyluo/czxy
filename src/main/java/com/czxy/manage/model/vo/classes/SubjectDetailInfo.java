package com.czxy.manage.model.vo.classes;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class SubjectDetailInfo extends CourseInfo {
    private String teacherName;
    @ApiModelProperty("若是点位，则有地址")
    private String address;
    @ApiModelProperty("在课表的第几天")
    private Integer offset;
    @ApiModelProperty("课程类型 0-课程，1-点位，2-其他")
    private Integer category;
    @JsonFormat(pattern = "yyyy.MM.dd hh:mm", timezone = "GMT+8")
    private Date beginTime;
    @JsonFormat(pattern = "yyyy.MM.dd hh:mm", timezone = "GMT+8")
    private Date endTime;
}
