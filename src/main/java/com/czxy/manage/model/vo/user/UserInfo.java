package com.czxy.manage.model.vo.user;

import com.czxy.manage.model.entity.BaseEntity;
import lombok.Data;

import java.util.Date;

/**
 * @Author lufy
 * @Description ...
 * @Date 20-4-16 下午9:10
 */
@Data
public class UserInfo extends BaseEntity {
    private int id;
    private String name;
    private String idCard;
    private String phone;
    private int gender;
    private String position;
    private int age;
    private Date birthday;
    private int category;
    private String wechatId;
}
