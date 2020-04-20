package com.czxy.manage.model.vo.user;

import com.czxy.manage.model.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @Author lufy
 * @Description ...
 * @Date 20-4-16 下午9:10
 */
@Data
public class UserAccountInfo extends UserInfo {
    private String account;
    private String password;
}
