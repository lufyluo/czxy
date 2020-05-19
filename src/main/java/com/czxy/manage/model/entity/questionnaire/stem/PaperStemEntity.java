package com.czxy.manage.model.entity.questionnaire.stem;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PaperStemEntity extends StemEntity{
    private Integer index;
    private Integer optionId;
    private String optionName;
    private Integer optionScore;
    private Integer optionIndex;
    private Integer stemId;
}
