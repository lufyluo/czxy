package com.czxy.manage.model.vo.classes;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ClassOrgInfo extends ClassInfo {
    private String orgName;
    private String recommendOrgName;
    private String leaderName;
    private Integer leaderId;
}
