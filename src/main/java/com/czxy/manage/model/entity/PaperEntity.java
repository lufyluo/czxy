package com.czxy.manage.model.entity;

import lombok.Data;

import java.util.Date;

@Data
public class PaperEntity {
    private Integer id;
    private String name;
    private String description;
    private Integer type;
    private Date updatedTime;
    private Date createdTime;
}
