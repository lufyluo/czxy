package com.czxy.manage.model.entity;

import lombok.Data;

@Data
public class CourseArrangeEntity extends BaseEntity {
    private Integer id;
    private Integer arrangeId;
    private Integer contentId;
    private Long beginTime;
    private Long endTime;
    private Integer category;
    private Integer offset;
}
