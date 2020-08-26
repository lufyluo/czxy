package com.czxy.manage.model.entity;

import lombok.Data;

@Data
public class ChatContentEntity {
    private Long id;
    private Integer senderId;
    private String content;
    private Integer fileId;
    private Integer classId;
}
