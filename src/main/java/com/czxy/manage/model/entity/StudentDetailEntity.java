package com.czxy.manage.model.entity;

import lombok.Data;

@Data
public class StudentDetailEntity extends StudentUserEntity{
    private String className;
    private String beginTime;
    private String endTime;
    private String orgName;
    private Integer duration;

}
