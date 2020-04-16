package com.czxy.manage.model.entity;

import lombok.Data;

@Data
public class StudentEntity extends BaseEntity {
    private int id;
    private String signFlag;
    private int classId;
}
