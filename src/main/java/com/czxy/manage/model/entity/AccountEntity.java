package com.czxy.manage.model.entity;

import lombok.Data;

@Data
public class AccountEntity extends BaseEntity{
    private int id;
    private String account;
    private String password;
    private int userId;
}
