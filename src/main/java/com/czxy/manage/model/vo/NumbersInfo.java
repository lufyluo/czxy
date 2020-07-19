package com.czxy.manage.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class NumbersInfo {
    @ApiModelProperty("学员数量")
    private Integer studentNumbers;
    @ApiModelProperty("教师数量")
    private Integer teacherNumbers;
    @ApiModelProperty("课题数量")
    private Integer subjectNumbers;
    @ApiModelProperty("点位数量")
    private Integer siteNumbers;
}
