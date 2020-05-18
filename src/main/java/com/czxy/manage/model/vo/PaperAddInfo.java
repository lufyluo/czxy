package com.czxy.manage.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
public class PaperAddInfo {
    @ApiModelProperty("题目ID")
    private Integer id;
    @ApiModelProperty("试卷题目序号")
    private Integer index;
    @ApiModelProperty("试卷ID")
    private Integer paperId;

}
