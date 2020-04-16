package com.czxy.manage.model.entity;

import lombok.Data;

@Data
public class TypeEntity extends BaseEntity {
    private int id;
    private String name;
    private String description;
    private int category;
}
