package com.czxy.manage.model.entity;

import lombok.Data;

@Data
public class UserMenuEntity extends BaseEntity {
    private Integer id;
    private Integer userId;
    private String MenuCodes;
}
