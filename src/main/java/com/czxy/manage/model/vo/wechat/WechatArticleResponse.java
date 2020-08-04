package com.czxy.manage.model.vo.wechat;

import lombok.Data;

@Data
public class WechatArticleResponse{
    private Integer errcode;
    private String errmsg;
    private String type;
    private String media_id;
    private Long created_at;
}
