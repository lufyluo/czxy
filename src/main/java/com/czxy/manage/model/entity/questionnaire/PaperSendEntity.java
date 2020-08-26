package com.czxy.manage.model.entity.questionnaire;

import lombok.Data;

@Data
public class PaperSendEntity {
    private Long Id;
    private Integer isToAll;
    private Integer userId;
    private Integer paperId;
    private Integer state;
}
