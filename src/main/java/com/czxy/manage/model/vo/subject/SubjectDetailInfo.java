package com.czxy.manage.model.vo.subject;

import com.czxy.manage.model.entity.SubjectEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SubjectDetailInfo {
    private Integer id;
    private String content;
    private String teacherName;
    private String typeNames;
    private String name;
    private String filesName;
    private Integer category;
    @ApiModelProperty(hidden = true)
    @JsonIgnore
    private String files;
    @ApiModelProperty(hidden = true)
    @JsonIgnore
    private String types;
}
