package com.czxy.manage.model.vo.classes;

import com.czxy.manage.model.entity.BaseEntity;
import com.czxy.manage.model.vo.site.TypeInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

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
    private Integer recommendOrgId;
    @ApiModelProperty("培训对象（班级成分）")
    private String composition;
    @ApiModelProperty("培训周期")
    private Integer duration;
    @ApiModelProperty("主题")
    private List<TypeInfo> topicInfos;
    @ApiModelProperty(hidden = true)
    @JsonIgnore
    private String topics;
}
