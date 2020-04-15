package com.czxy.manage.model.entity;

import lombok.Data;

import java.util.Date;

/**
 * @Author lufy
 * @Description ...
 * @Date 20-4-15 下午9:48
 */
@Data
public class BaseEntity {
    private Date updatedTime;
    private Date createdTime;
}
