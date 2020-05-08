package com.czxy.manage.model.vo.student;

import com.czxy.manage.model.vo.user.UserInfo;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class StudentAddInfo extends UserInfo {
    @ApiModelProperty("中文主办方，推荐单位")
    private String orgName;
    @ApiModelProperty("0-学员；1-班委干部；8-带班领导")
    @NotNull(message = "学生类型不能为空:0-学员；1-班委干部；8-带班领导")
    private Integer type;
    private Integer classId;
    @ApiModelProperty("班级名称，若有班级id则不用，无班级id，会根据name自动关联班级")
    private String className;
    @ApiModelProperty("签到状态，0代表未签到，1代表签到")
    private Integer signFlag;
    private Integer userId;
}
