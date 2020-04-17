package com.czxy.manage.model.entity;

import lombok.Data;

@Data
public class AccountEntity extends BaseEntity{
    private Integer id;
    private String account;
    private String password;
    private Integer userId;
}
