package com.czxy.manage.model.entity;

import lombok.Data;

@Data
public class QuestionnaireEntity extends  BaseEntity {
    private int id;
    private String name;
    private int classId;
}
