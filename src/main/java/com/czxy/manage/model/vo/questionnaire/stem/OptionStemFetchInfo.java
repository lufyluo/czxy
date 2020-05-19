package com.czxy.manage.model.vo.questionnaire.stem;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class OptionStemFetchInfo {
    private Integer id;
    @ApiModelProperty("选项,选项没有编辑，保存后只能删了再建")
    private List<OptionInfo> options;
    @ApiModelProperty(hidden = true)
    @JsonIgnore
    private Integer optionId;
    @ApiModelProperty(hidden = true)
    @JsonIgnore
    private String optionName;
    @ApiModelProperty(hidden = true)
    @JsonIgnore
    private Integer optionScore;
    @ApiModelProperty(hidden = true)
    @JsonIgnore
    private Integer optionIndex;
    @ApiModelProperty(hidden = true)
    @JsonIgnore
    private Integer stemId;
    @ApiModelProperty(hidden = true)
    @JsonIgnore
    private Integer index;
}
