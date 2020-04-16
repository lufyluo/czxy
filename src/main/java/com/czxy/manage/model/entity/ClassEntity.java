package com.czxy.manage.model.entity;

import lombok.Data;

import java.util.Date;
@Data
public class ClassEntity extends BaseEntity {
    private int id;
    private String name;
    private int teacherId;
    private int addressId;
    private int org;
    private Date beginTime;
    private Date endTime;
    private int arrangeId;
}
