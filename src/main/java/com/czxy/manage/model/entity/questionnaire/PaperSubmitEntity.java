package com.czxy.manage.model.entity.questionnaire;

import lombok.Data;


@Data
public class PaperSubmitEntity {
    private Integer id;
    private Integer paperId;
    private Integer stemId;
    private Integer optionId;
    private Integer userId;
    private String answerText;
}
