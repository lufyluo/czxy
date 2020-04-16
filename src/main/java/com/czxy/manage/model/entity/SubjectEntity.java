package com.czxy.manage.model.entity;

import lombok.Data;

@Data
public class SubjectEntity extends BaseEntity {
    private int id;
    private String name;
    private String content;
    private String types;
    private String file;
    private int teacherId;
}
