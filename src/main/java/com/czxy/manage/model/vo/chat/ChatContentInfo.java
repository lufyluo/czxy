package com.czxy.manage.model.vo.chat;

import lombok.Data;

@Data
public class ChatContentInfo {
    private Long id;
    private Integer senderId;
    private String senderName;
    private String content;
    private String url;
    private Integer fileId;
    private Integer classId;
}
