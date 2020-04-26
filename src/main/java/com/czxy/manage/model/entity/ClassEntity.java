package com.czxy.manage.model.entity;

import lombok.Data;

import java.util.Date;
@Data
public class ClassEntity extends BaseEntity {
    private Integer id;
    private String name;
    private Integer addressId;
    private Integer orgId;
    private Date beginTime;
    private Date endTime;
    private Integer arrangeId;
    private String topics;
    private Integer recommendOrgId;
    private Integer compositionId;
    private Integer duration;
}
