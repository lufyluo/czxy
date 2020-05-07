package com.czxy.manage.model.vo.classes;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class CourseArrangeInfo {
    @ApiModelProperty("配合category使用，当category是0，则为课题id，category为1，则为点位id")
    private Integer contentId;
    @ApiModelProperty("课程方式，0-课题，1-点位")
    private Byte category;
    @ApiModelProperty("该课所在课程表当顺序")
    private Integer offset;
    @ApiModelProperty("开始时间yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date beginTime;
    @ApiModelProperty("结束时间yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date endTime;
}
