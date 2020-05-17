package com.czxy.manage.model.entity.questionnaire.stem;

import com.czxy.manage.model.entity.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class OptionEntity extends BaseEntity {
    private Integer id;
    @ApiModelProperty("选项名称")
    private String name;
    @ApiModelProperty("0-普通题目，1-分数题目")
    private Integer type;
    @ApiModelProperty("选项分数")
    private Integer score;
    @ApiModelProperty("选项顺序")
    private Integer index;
    private Integer stemId;
}
