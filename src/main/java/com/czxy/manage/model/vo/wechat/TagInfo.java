package com.czxy.manage.model.vo.wechat;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;


@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TagInfo extends WechatResponse{
    private String name;
    private Integer id;
    private Long count;
}
