package com.czxy.manage.model.entity;

import lombok.Data;

@Data
public class CourseEntity extends BaseEntity {
    private Integer id;
    private String name;
    private String description;
}
