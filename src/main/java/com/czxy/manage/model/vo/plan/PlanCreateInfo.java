package com.czxy.manage.model.vo.plan;

import com.czxy.manage.model.vo.site.TypeInfo;
import lombok.Data;

import java.util.List;

@Data
public class PlanCreateInfo {
    private String name;
    private String typeName;
    private Integer arrangeId;
    private List<TypeInfo> typeInfos;
    private String description;
    private List<Integer> fileIds;
}
