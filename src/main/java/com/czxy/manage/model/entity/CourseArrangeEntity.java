package com.czxy.manage.model.entity;

import lombok.Data;

@Data
public class CourseArrangeEntity extends BaseEntity {
    private int id;
    private int courseId;
    private int contentId;
    private int beginTime;
    private int endTime;
    private int category;

}
