package com.czxy.manage.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @Author lufy
 * @Description ...
 * @Date 20-4-15 下午9:44
 */
@Data
public class UserEntity extends BaseEntity{
    private Integer id;
    private String name;
    private String idCard;
    private String phone;
    private Integer gender;
    private String position;
    private Integer age;
    private Date birthday;
    private Integer category;
    private String wechatId;
    private Integer orgId;
    private String nation;
    private String nativePlace;
}
