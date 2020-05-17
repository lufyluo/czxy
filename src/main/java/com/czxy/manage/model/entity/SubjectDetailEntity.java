package com.czxy.manage.model.entity;

import lombok.Data;

@Data
public class SubjectDetailEntity {
    private Integer id;
    private String content;
    private String teacherName;
    private String name;
    private String types;
    private String files;
    private Integer category;
}
