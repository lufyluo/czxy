package com.czxy.manage.model.vo.student;

import com.czxy.manage.model.entity.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class StudentInfo extends BaseEntity {
    private Integer id;
    @ApiModelProperty("签到状态，0代表未签到，1代表签到")
    private String signFlag;
    private Integer classId;
    private Integer userId;
    @ApiModelProperty("0-学员；1-班委干部；8-带班领导")
    private Integer type;
}
