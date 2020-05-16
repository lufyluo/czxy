package com.czxy.manage.model.entity;

import lombok.Data;

@Data
public class PlanEntity extends BaseEntity {
    private Integer id;
    private Integer arrangeId;
    private String files;
    private String name;
    private String types;
    private String topics;
}
