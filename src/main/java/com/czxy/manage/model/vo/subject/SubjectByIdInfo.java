package com.czxy.manage.model.vo.subject;

import com.czxy.manage.model.entity.BaseEntity;
import com.czxy.manage.model.vo.files.FileInfo;
import com.czxy.manage.model.vo.site.TypeInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import lombok.Data;

import java.util.List;

@Data
public class SubjectByIdInfo extends BaseEntity {
    private Integer id;
    private String content;
    private Integer category;
    @ApiModelProperty("课题名称")
    private String name;
    @ApiModelProperty("教师名字")
    private String teacherName;
    private Integer teacherId;
    private String description;
    @ApiModelProperty("类型")
    private List<TypeInfo> types;
    @ApiModelProperty("类型")
    public List<FileInfo> fileInfos;
    @ApiModelProperty(hidden = true)
    @JsonIgnore
    private String typeIds;
}
