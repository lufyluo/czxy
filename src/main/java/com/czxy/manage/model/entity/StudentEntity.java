package com.czxy.manage.model.entity;

import lombok.Data;

@Data
public class StudentEntity extends BaseEntity {
    private Integer id;
    private Integer signFlag;
    private Integer classId;
    private Integer userId;
    private Integer type;
    private String orgName;
    private Integer orgId;
}
