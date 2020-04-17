package com.czxy.manage.model.entity;

import lombok.Data;

@Data
public class AddressEntity extends BaseEntity {
    private Integer id;
    private String name;
    private Integer parentId;
    private Integer category;
    private String code;
}
