package com.czxy.manage.model.entity;

import com.czxy.manage.model.vo.classes.ClassInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ClassInformationEntity extends ClassEntity{
    @ApiModelProperty("班级主办方")
    private String orgName;
    private String addressName;
    private String masterName;
    private String leaderName;
    @ApiModelProperty("推荐单位")
    private String recommendOrgName;
}
