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
public class UserInfo extends BaseEntity {
    private Integer id;
    private String name;
    private String idCard;
    private String phone;
    private Integer gender;
    private String position;
    private Integer age;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date birthday;
    private Integer category;
    private String wechatId;
}
