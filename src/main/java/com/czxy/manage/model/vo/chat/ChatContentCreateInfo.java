package com.czxy.manage.model.vo.chat;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;

@Data
public class ChatContentCreateInfo {
    @ApiModelProperty("发送人userId")
    @Min(value = 1,message = "发送人错误")
    private Integer senderId;
    @ApiModelProperty("文字内容")
    private String content;
    @ApiModelProperty("图片id")
    private String fileId;
    @ApiModelProperty("班级id")
    @Min(value = 1,message = "班级id错误！")
    private Integer classId;
}
