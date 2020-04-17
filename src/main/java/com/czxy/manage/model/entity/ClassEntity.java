package com.czxy.manage.model.entity;

import lombok.Data;

import java.util.Date;
@Data
public class ClassEntity extends BaseEntity {
    private Integer id;
    private String name;
    private Integer teacherId;
    private Integer addressId;
    private Integer org;
    private Date beginTime;
    private Date endTime;
    private Integer arrangeId;
}
