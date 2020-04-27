package com.czxy.manage.model.vo.student;

import com.czxy.manage.model.vo.user.UserInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class StudentDetailInfo extends UserInfo {
    @ApiModelProperty("学生id")
    private Integer studentId;
    private String className;
    @ApiModelProperty("培训时间")
    private String duration;
    @ApiModelProperty("是否签到")
    private Boolean sign;
    @ApiModelProperty("是否签到")
    private String studentIdentity;
}
