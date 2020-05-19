package com.czxy.manage.model.vo.questionnaire.stem;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class StemInfo extends OptionStemFetchInfo{
    private Integer id;
    @ApiModelProperty("题目类型，单选、多选、问答")
    private String type;
    @ApiModelProperty("题目名称")
    private String title;
    @ApiModelProperty("0-非必选，1-必须选")
    private Integer required;
    @ApiModelProperty("题目总分数（非分数则不传）")
    private Integer score;
    @ApiModelProperty("0-普通题目，1-分数题目")
    private Integer category;
    @ApiModelProperty("选项,选项没有编辑，保存后只能删了再建")
    private List<OptionInfo> options;
}
