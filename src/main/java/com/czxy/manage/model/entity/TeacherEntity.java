package com.czxy.manage.model.entity;

import lombok.Data;

@Data
public class TeacherEntity extends BaseEntity {
    private int id;
    private int score;
    private String comment;
}
