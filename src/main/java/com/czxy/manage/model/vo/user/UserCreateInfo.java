package com.czxy.manage.model.vo.user;

import lombok.Data;

/**
 * @Author lufy
 * @Description ...
 * @Date 20-4-16 下午9:10
 */
@Data
public class UserCreateInfo extends UserInfo {
    private String account;
    private String password;
}
