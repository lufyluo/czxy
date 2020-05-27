package com.czxy.manage.model.entity;

import lombok.Data;

@Data
public class PaperDetailEntity {
    private Integer id;
    private String name;
    private String description;

    private Integer stemId;
    private String type;
    private Integer index;
    private String title;
    private Integer required;
    private Integer score;
    private Integer category;

    private Integer optionId;
    private String optionName;
    private Integer optionScore;
    private Integer optionIndex;
    private Integer optionSelected;

    private Integer answerId;
    private String answerText;
}
