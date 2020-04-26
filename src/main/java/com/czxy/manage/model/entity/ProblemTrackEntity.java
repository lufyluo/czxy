package com.czxy.manage.model.entity;

import lombok.Data;

import java.util.Date;

@Data
public class ProblemTrackEntity extends BaseEntity {
    private Integer id;
    private String feedback;
    private Date metionTime;
    private String advise;
    private Integer status;
    private String remark;
    private Integer sourceType;
}
