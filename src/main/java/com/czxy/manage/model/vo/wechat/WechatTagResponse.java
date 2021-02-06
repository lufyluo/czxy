package com.czxy.manage.model.vo.wechat;

import lombok.Data;

@Data
public class WechatTagResponse extends WechatResponse{
    private Integer count;
    private WechatTagInfo data;
}
