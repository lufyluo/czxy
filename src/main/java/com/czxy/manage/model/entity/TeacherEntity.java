package com.czxy.manage.model.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TeacherEntity extends BaseEntity {
    private Integer id;
    private Integer teacherId;
    private Integer score;
    private String comment;
    private Integer userId;
    private String system;
    private String skill;
    private String star;
    private String area;
    private Integer pay;
}
