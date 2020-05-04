package com.czxy.manage.model.vo.classes;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ClassOrgInfo extends ClassInfo {
    @ApiModelProperty("主办方中文名称")
    private String orgName;
    @ApiModelProperty("推荐单位中文名称")
    private String recommendOrgName;
    @ApiModelProperty("带队人，带队领导")
    private String leaderName;
    private Integer leaderId;
}
