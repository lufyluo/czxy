package com.czxy.manage.model.entity;

import lombok.Data;

@Data
public class PaperCopyEntity {
    private Integer id;
    private String name;
    private String description;
    private Integer state;
    private Integer index;
    private String title;
    private Integer score;
    private String optionName;
    private Integer optionScore;
    private Integer optionIndex;
}
