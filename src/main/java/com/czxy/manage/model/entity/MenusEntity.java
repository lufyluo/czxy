package com.czxy.manage.model.entity;

import lombok.Data;

@Data
public class MenusEntity extends BaseEntity {
    private Integer id;
    private Integer parentId;
    private String name;
    private String code;
}
