package com.czxy.manage.model.vo.classes;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ClassInformationInfo extends ClassInfo{
    @ApiModelProperty("班级主办方")
    private String orgName;
    private String addressName;
    private String masterName;
    private String leaderName;
    @ApiModelProperty("推荐单位")
    private String recommendOrgName;
}
