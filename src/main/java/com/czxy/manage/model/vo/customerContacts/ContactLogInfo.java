package com.czxy.manage.model.vo.customerContacts;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class ContactLogInfo {
    private Integer id;
    private Integer planId;
    @ApiModelProperty("内容")
    private String content;
    @ApiModelProperty("联系时间")
    @JsonFormat(pattern = "yyyy年MM月dd日 hh点mm分")
    private Date createdTime;
}
