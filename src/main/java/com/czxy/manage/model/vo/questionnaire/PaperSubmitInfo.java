package com.czxy.manage.model.vo.questionnaire;

import lombok.Data;

import javax.validation.constraints.Min;
import java.util.List;

@Data
public class PaperSubmitInfo {
    private Integer id;
    @Min(value = 1,message = "paperId 不能为空")
    private Integer paperId;
    @Min(value = 1,message = "stemId 不能为空")
    private Integer stemId;
    private List<Integer> optionIds;
    private Integer userId;
    private String answerText;
}
