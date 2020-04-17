package com.czxy.manage.model.entity;

import lombok.Data;

@Data
public class PlanEntity extends BaseEntity {
    private Integer id;
    private Integer arrangeId;
    private String file;
    private String name;
    private String types;
    private String topics;
}
