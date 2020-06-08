package com.czxy.manage.model.entity;

import lombok.Data;

@Data
public class ClassOrgEntity extends ClassEntity{
    private String orgName;
    private String recommendOrgName;
    private String composition;
    private Integer masterId;
    private String masterName;
    private String leaderName;
    private Integer leaderId;
    private String arrangeName;
    private Integer studentCount;
}
