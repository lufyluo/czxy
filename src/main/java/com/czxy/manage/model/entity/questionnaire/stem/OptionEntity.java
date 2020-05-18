package com.czxy.manage.model.entity.questionnaire.stem;

import com.czxy.manage.model.entity.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class OptionEntity extends BaseEntity {
    private Integer id;
    @ApiModelProperty("选项名称")
    private String name;
    @ApiModelProperty("选项分数")
    private Integer score;
    @ApiModelProperty("选项顺序")
    private Integer index;
    private Integer stemId;
}
