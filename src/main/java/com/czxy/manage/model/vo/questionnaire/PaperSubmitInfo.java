package com.czxy.manage.model.vo.questionnaire;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class PaperSubmitInfo {
    private Integer id;
    private Integer paperId;
    private Integer stemId;
    private Integer optionId;
    private Integer userId;
    private String answerText;
    private Date updatedTime;
    private Date createdTime;
}
