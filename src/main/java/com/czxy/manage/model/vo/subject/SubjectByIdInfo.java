package com.czxy.manage.model.vo.subject;

import com.czxy.manage.model.entity.BaseEntity;
import com.czxy.manage.model.vo.site.TypeInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class SubjectByIdInfo extends BaseEntity {
    @ApiModelProperty("课题名称")
    private String name;
    @ApiModelProperty("教师名字")
    private String teacherName;
    @ApiModelProperty("类型")
    private List<TypeInfo> types;
    @ApiModelProperty(hidden = true)
    @JsonIgnore
    private String typeIds;
}
