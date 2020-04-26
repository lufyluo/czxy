package com.czxy.manage.model.entity;

import lombok.Data;

import java.util.Date;

@Data
public class ClassMasterEntity extends BaseEntity {
    private Integer id;
    private Integer userId;
    private Integer type;
    private Integer classId;
}
