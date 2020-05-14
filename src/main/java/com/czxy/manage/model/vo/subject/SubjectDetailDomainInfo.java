package com.czxy.manage.model.vo.subject;

import com.czxy.manage.model.vo.classes.CourseSubjectDetailInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SubjectDetailDomainInfo extends CourseSubjectDetailInfo {
    @ApiModelProperty("yyyy年MM月dd日")
    private String date;
    @ApiModelProperty("例如：08:00-12:00")
    private String time;
}
