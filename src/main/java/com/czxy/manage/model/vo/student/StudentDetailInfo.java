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
    private Integer classId;
    @ApiModelProperty("培训时长")
    private Integer duration;
    @ApiModelProperty("培训区间中文描述")
    private String period;
    @JsonIgnore
    private Date beginTime;
    @JsonIgnore
    private Date endTime;
    @ApiModelProperty("是否签到")
    private Boolean sign;
    @JsonIgnore
    @ApiModelProperty(hidden = true)
    private Integer signFlag;
    @ApiModelProperty("学员身份")
    private String studentIdentity;
    @ApiModelProperty("学员身份ID")
    private Integer type;
    @ApiModelProperty("工作单位中文名称")
    private String orgName;
    @ApiModelProperty("用户id")
    private Integer userId;
}
