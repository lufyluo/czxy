package com.czxy.manage.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class PaperAddInfo {
    @ApiModelProperty("stem表ID")
    private Integer id;
    @ApiModelProperty("试卷题目序号")
    private Integer index;
    @ApiModelProperty("题目ID")
    private Integer stemId;
    @ApiModelProperty("试卷ID")
    private Integer paperId;

}
