package com.czxy.manage.model.vo.wechat;

import lombok.Data;


@Data
public class TagInfo extends WechatResponse{
    private String name;
    private Integer id;
    private Long count;
}
