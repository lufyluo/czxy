package com.czxy.manage.model.entity;

import lombok.Data;

@Data
public class PaperCreateEntity {
    private Integer id;
    private String name;
    private String description;
    private Integer index;
    private Integer stemId;
}
