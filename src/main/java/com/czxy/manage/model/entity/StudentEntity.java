package com.czxy.manage.model.entity;

import lombok.Data;

@Data
public class StudentEntity extends BaseEntity {
    private Integer id;
    private String signFlag;
    private Integer classId;
    private Integer userId;
    private Integer type;
    private Integer orgId;
}
