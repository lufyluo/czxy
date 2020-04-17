package com.czxy.manage.model.entity;

import lombok.Data;

@Data
public class TeacherEntity extends BaseEntity {
    private Integer id;
    private Integer score;
    private String comment;
}
