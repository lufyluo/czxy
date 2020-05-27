package com.czxy.manage.model.vo.questionnaire.stem;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class OptionInfo {
    private Integer id;
    @ApiModelProperty("选项名称")
    private String name;
    @ApiModelProperty("选项分数")
    private Integer score;
    @ApiModelProperty("选项顺序")
    private Integer index;
    @ApiModelProperty("简答提答案")
    private String answerText;
}
