package com.czxy.manage.model.vo.student;

import com.czxy.manage.model.vo.user.UserInfo;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class StudentAddInfo extends UserInfo {
    private String orgName;
    private Integer type;
    private Integer classId;
    private Integer signFlag;
    private Integer userId;
}
