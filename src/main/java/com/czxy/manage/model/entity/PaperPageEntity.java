package com.czxy.manage.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class PaperPageEntity {
    @ApiModelProperty("试卷ID")
    private Integer id;
    private String name;
    private String description;
    @ApiModelProperty("0-用户未提交，1-用户已提交")
    private Integer state;
    @ApiModelProperty("问卷发送时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdTime;
}
