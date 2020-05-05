package com.czxy.manage.model.vo.subject;

import com.czxy.manage.model.entity.BaseEntity;
import lombok.Data;

import java.util.List;

@Data
public class SubjectInfo extends BaseEntity {
    private String name;
    private Integer typesId;
    private String typeName;
    private Integer teacherId;
    private List<Integer> fileIds;
    private Integer category;
}
