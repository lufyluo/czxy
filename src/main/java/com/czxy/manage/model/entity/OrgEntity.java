package com.czxy.manage.model.entity;

import lombok.Data;

@Data
public class OrgEntity extends BaseEntity {
    private int id;
    private int name;
    private int addressId;
}
