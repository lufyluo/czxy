package com.czxy.manage.model.entity;

import lombok.Data;

@Data
public class QuestionrecieveEntity extends BaseEntity {
    private int id;
    private String recieverId;
    private int questionitemId;
    private int score;
    private int content;
}
