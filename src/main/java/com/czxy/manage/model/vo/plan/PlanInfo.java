package com.czxy.manage.model.vo.plan;

import com.czxy.manage.model.vo.site.TypeInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class PlanInfo {
    private Integer id;
    @ApiModelProperty("名称")
    private String name;
    @ApiModelProperty(hidden = true)
    @JsonIgnore
    private String types;
    @ApiModelProperty(value = "方案类型", hidden = true)
    private List<TypeInfo> typeInfos;
    @ApiModelProperty("方案类型，前端显示字段")
    private String typeNames;
    @ApiModelProperty("描述")
    private String description;
}
