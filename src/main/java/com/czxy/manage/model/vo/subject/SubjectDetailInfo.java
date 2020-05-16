package com.czxy.manage.model.vo.subject;

import com.czxy.manage.model.entity.SubjectEntity;
import lombok.Data;

@Data
public class SubjectDetailInfo {
    private Integer id;
    private String teacherName;
    private String typeName;
    private String name;
    private String filesName;
    private String files;
    private String types;
}
