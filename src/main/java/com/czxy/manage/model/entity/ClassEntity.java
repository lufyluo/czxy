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
    private Integer recommendOrgId;
    @ApiModelProperty("培训对象(班级成分)")
    private Integer compositionId;
    private Integer duration;
    private String addr;
    private String classroom;
    @ApiModelProperty("班级互动区开关,0-可用，1-不可用")
    private Byte chatOff;
}
