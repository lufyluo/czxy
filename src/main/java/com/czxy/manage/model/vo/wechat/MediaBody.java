package com.czxy.manage.model.vo.wechat;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class MediaBody {
    @ApiModelProperty("素材的类型，图片（image）、视频（video）、语音 （voice）、图文（news")
    private String type;
    private String offset;
    private String count;
}
