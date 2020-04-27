package com.czxy.manage.model.vo.student;

import com.czxy.manage.model.vo.user.UserInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class StudentDetailInfo extends UserInfo {
    @ApiModelProperty("学生id")
    private Integer studentId;
    private String className;
    @ApiModelProperty("培训时间")
    private String duration;
    @JsonIgnore
    private Date beginTime;
    @JsonIgnore
    private Date endTime;
    @ApiModelProperty("是否签到")
    private Boolean sign;
    @JsonIgnore
    private Integer signFlag;
    @ApiModelProperty("学员身份")
    private String studentIdentity;
    @JsonIgnore
    private Integer type;

}
