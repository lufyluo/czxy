package com.czxy.manage.model.entity;

import lombok.Data;

@Data
public class MenusEntity extends BaseEntity {
    private int id;
    private int parentId;
    private String name;
    private String code;
}
