package com.czxy.manage.model.entity;

import lombok.Data;

import java.util.Date;

/**
 * @Author lufy
 * @Description ...
 * @Date 20-4-15 下午9:44
 */
@Data
public class UserEntity extends BaseEntity{
    private int id;
    private String name;
    private String idcard;
    private String phone;
    private int gender;
    private String position;
    private int age;
    private Date birthday;
    private int category;
    private String wechatId;
}
