package com.czxy.manage.model.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
@Data
public class ClassEntity extends BaseEntity {
    private Integer id;
    private String name;
    private Integer provinceId;
    private Integer cityId;
    private Integer countyId;
    private Integer orgId;
    private Date beginTime;
    private Date endTime;
    private Integer arrangeId;
    private String topics;
    private Integer recommendOrgId;
    @ApiModelProperty("培训对象(班级成分)")
    private String appellation;
    private Integer duration;
    private String addr;
}
