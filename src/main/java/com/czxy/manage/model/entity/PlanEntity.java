package com.czxy.manage.model.entity;

import lombok.Data;

@Data
public class PlanEntity extends BaseEntity {
    private int id;
    private int arrangeId;
    private String file;
    private String name;
    private String types;
    private String topics;
}
