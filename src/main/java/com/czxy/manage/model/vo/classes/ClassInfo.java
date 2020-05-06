package com.czxy.manage.model.vo.classes;

import com.czxy.manage.model.entity.BaseEntity;
import com.czxy.manage.model.vo.site.TypeInfo;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class ClassInfo extends BaseEntity {
    private Integer id;
    private String name;
    @ApiModelProperty("省id")
    private Integer provinceId;
    @ApiModelProperty("市id")
    private Integer cityId;
    @ApiModelProperty("区、县id")
    private Integer countyId;
    @ApiModelProperty("主办方id")
    private Integer orgId;
    @ApiModelProperty("开始时间yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date beginTime;
    @ApiModelProperty("结束时间yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
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
