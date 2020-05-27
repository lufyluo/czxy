package com.czxy.manage.model.vo.message;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class MessageInfo {
    private String title;
    private String message;
    @ApiModelProperty("发送时间")
    @JsonFormat(pattern = "yyyy-MM-dd hh:ss")
    private Date createdTime;
}
