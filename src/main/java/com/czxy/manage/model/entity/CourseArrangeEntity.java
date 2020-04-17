package com.czxy.manage.model.entity;

import lombok.Data;

@Data
public class CourseArrangeEntity extends BaseEntity {
    private Integer id;
    private Integer courseId;
    private Integer contentId;
    private Integer beginTime;
    private Integer endTime;
    private Integer category;

}
