package com.czxy.manage.model.entity;

import lombok.Data;

@Data
public class QuestionrecieveEntity extends BaseEntity {
    private Integer id;
    private String recieverId;
    private Integer questionitemId;
    private Integer score;
    private Integer content;
}
