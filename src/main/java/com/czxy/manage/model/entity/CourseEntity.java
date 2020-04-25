package com.czxy.manage.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class CourseEntity extends BaseEntity {
    private Integer id;
    private String name;
    private String description;
    private Integer type;
    private Integer teacherId;
}
