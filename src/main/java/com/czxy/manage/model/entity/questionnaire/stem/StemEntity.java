package com.czxy.manage.model.entity.questionnaire.stem;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class StemEntity {
    private Integer id;
    @ApiModelProperty("题目类型，单选、多选、问答")
    private String type;
    @ApiModelProperty("题目名称")
    private String title;
    @ApiModelProperty("0-非必选，1-必须选")
    private Integer required;
    @ApiModelProperty("题目总分数（非分数则不传）")
    private Integer score;
}
