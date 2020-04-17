package com.czxy.manage.model.entity;

import lombok.Data;

@Data
public class QuestionnaireEntity extends  BaseEntity {
    private Integer id;
    private String name;
    private Integer classId;
}
