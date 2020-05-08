package com.czxy.manage.model.entity;

import lombok.Data;

import java.util.Date;

@Data
public class StudentDetailEntity extends StudentUserEntity{
    private String className;
    private Date beginTime;
    private Date endTime;
    private String orgName;
    private Integer duration;
    private Integer studentId;

}
