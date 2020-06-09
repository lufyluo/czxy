package com.czxy.manage.model.entity.classFile;

import com.czxy.manage.model.entity.BaseEntity;
import lombok.Data;

@Data
public class ClassFileEntity extends BaseEntity {
    private Integer id;
    private Integer classId;
    private Integer fileId;
}
