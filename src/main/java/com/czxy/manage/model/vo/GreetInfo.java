package com.czxy.manage.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class GreetInfo {
    private Integer id;
    @ApiModelProperty("问候语类型")
    private String type;
    @ApiModelProperty("节假日名称")
    private String name;
    @ApiModelProperty("问候语")
    private String greet;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @ApiModelProperty("问候语发送时间")
    private Date sendTime;
}
