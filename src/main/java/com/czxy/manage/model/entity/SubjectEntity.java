package com.czxy.manage.model.entity;

import lombok.Data;

@Data
public class SubjectEntity extends BaseEntity {
    private Integer id;
    private String name;
    private String content;
    private String types;
    private String typeIds;
    private String files;
    private Integer teacherId;
    private String teacherName;
    private Integer category;
    private String description;
}
