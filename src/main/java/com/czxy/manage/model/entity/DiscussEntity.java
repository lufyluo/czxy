package com.czxy.manage.model.entity;

import lombok.Data;

@Data
public class DiscussEntity extends BaseEntity {
    private Integer id;
    private Integer userId;
    private Integer classId;
    private Integer arrangeId;
    private String content;
    private Integer parentId;
    private Integer category;
}
