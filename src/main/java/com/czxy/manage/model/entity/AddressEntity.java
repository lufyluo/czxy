package com.czxy.manage.model.entity;

import lombok.Data;

@Data
public class AddressEntity extends BaseEntity {
    private int id;
    private String name;
    private int parentId;
    private int category;
    private String code;
}
