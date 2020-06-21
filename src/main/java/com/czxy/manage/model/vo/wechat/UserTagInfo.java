package com.czxy.manage.model.vo.wechat;

import lombok.Data;

import java.util.List;

@Data
public class UserTagInfo {
    private List<String> openid_list;
    private Integer tagid;
}
