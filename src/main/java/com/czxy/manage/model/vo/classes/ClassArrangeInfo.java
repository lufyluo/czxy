package com.czxy.manage.model.vo.classes;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class ClassArrangeInfo {
    private Integer id;
    @ApiModelProperty("课程表名称")
    private String name;
    @ApiModelProperty("课程描述")
    private String description;
    @ApiModelProperty("班级开始时间")
    private Date beginTime;
    @ApiModelProperty("班级结束时间")
    private Date endTime;
    @ApiModelProperty("课表状态")
    private String stateDesc;
    @ApiModelProperty("0-未使用，1-使用中")
    private Integer state;
    private List<CourseSubjectDetailInfo> courseInfos;
}
