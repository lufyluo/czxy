package com.czxy.manage.model.vo.customerContacts;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class ContactLogInfo {
    @ApiModelProperty("操作记录id")
    private Integer id;
    @ApiModelProperty("客户通讯录id")
    private Integer planId;
    @ApiModelProperty("内容")
    private String content;
    @ApiModelProperty("联系时间")
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date contactTime;
}
