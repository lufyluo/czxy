package com.czxy.manage.model.entity;

import lombok.Data;

@Data
public class DiscussEntity extends BaseEntity {
    private int id;
    private int userId;
    private int classId;
    private int arrangeId;
    private String content;
    private int parentId;
    private int category;
}
