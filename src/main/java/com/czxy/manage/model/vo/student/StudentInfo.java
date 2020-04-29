package com.czxy.manage.model.vo.student;

import com.czxy.manage.model.entity.BaseEntity;
import lombok.Data;

@Data
public class StudentInfo extends BaseEntity {
    private Integer id;
    private String signFlag;
    private Integer classId;
    private Integer userId;
    private Integer type;
}
