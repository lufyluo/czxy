package com.czxy.manage.model.entity;

import lombok.Data;

@Data
public class CourseEntity extends BaseEntity {
    private int id;
    private String name;
    private String description;
}
