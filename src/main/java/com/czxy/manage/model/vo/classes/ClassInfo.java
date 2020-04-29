package com.czxy.manage.model.vo.classes;

import com.czxy.manage.model.entity.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class ClassInfo extends BaseEntity {
    private Integer id;
    private String name;
    private Integer provinceId;
    private Integer cityId;
    private Integer countyId;
    private Integer orgId;
    private Date beginTime;
    private Date endTime;
    private Integer arrangeId;
    @ApiModelProperty("主题")
    private String topics;
    private Integer recommendOrgId;
    @ApiModelProperty("班级成分")
    private Integer compositionId;
    @ApiModelProperty("培训周期")
    private Integer duration;
    @ApiModelProperty("培训对象")
    private String appellation;
}
