package com.czxy.manage.model.entity;

import lombok.Data;

@Data
public class StudentUserEntity extends UserEntity {
    private Integer id;
    private Integer signFlag;
    private Integer classId;
    private Integer userId;
    private Integer type;
}
