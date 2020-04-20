package com.czxy.manage.model.entity;

import lombok.Data;

@Data
public class OrgEntity extends BaseEntity {
    private Integer id;
    private String name;
    private Integer addressId;
}
