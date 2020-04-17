package com.czxy.manage.model.entity;

import lombok.Data;

@Data
public class TypeEntity extends BaseEntity {
    private Integer id;
    private String name;
    private String description;
    private Integer category;
}
