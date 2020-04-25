package com.czxy.manage.model.entity;

import lombok.Data;

import java.util.Date;

@Data
public class ClassArrangeWithTimeEntity extends ArrangeEntity{
    private Date beginTime;
    private Date endTime;
}
