package com.czxy.manage.model.vo.questionnaire;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class OptionAnalysisDetailInfo extends OptionDetailInfo{
    @ApiModelProperty("改选项答案提交人数，问答题就是总提交人数")
    private int count;
    private String percent;
    @ApiModelProperty(hidden = true)
    @JsonIgnore
    private String answerText;
    @ApiModelProperty(hidden = true)
    @JsonIgnore
    private Boolean selected;
    @ApiModelProperty("若该题为问答题，则该字段才有值，为每个人提交的文本答案")
    private List<String> answersTexts;
}
