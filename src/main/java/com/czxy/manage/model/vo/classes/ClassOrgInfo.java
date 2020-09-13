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
    @ApiModelProperty("带队人，带队领导userId")
    private Integer leaderId;
    private String composition;
    private Integer compositionId;
    private Integer masterId;
    @ApiModelProperty("班主任姓名")
    private String masterName;
    private Integer assistantId;
    @ApiModelProperty("班主任助理")
    private String assistantName;
    @ApiModelProperty("课表名称")
    private String arrangeName;
    @ApiModelProperty("班级总人数")
    private Integer studentCount;

}
