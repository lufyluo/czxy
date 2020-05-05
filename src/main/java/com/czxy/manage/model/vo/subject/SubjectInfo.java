package com.czxy.manage.model.vo.subject;

import com.czxy.manage.model.entity.BaseEntity;
import com.czxy.manage.model.vo.site.TypeInfo;
import lombok.Data;

import java.util.List;

@Data
public class SubjectInfo extends BaseEntity {
    private String name;
    private List<TypeInfo> types;
    private Integer teacherId;
    private List<Integer> fileIds;
    private Integer category;
    private String content;
}
