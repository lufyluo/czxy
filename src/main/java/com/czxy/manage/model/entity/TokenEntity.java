package com.czxy.manage.model.entity;

import lombok.Data;

@Data
public class TokenEntity extends BaseEntity {
    private Integer id;
    private String account;
    private String token;
    private Integer expire;
}
