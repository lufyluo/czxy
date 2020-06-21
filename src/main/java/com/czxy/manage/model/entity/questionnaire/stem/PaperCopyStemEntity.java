package com.czxy.manage.model.entity.questionnaire.stem;

import lombok.Data;

import java.util.Date;

@Data
public class PaperCopyStemEntity {
    private Integer id;
    private Integer index;
    private Integer paperId;
    private Integer stemId;
    private Date updatedTime;
    private Date createdTime;
}
