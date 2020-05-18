package com.czxy.manage.model.entity.questionnaire.stem;

import lombok.Data;

@Data
public class StemOptionEntity extends StemEntity{
    private Integer optionId;
    private String optionName;
    private Integer optionScore;
    private Integer optionIndex;
    private Integer stemId;
}
