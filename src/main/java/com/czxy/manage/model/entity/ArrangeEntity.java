package com.czxy.manage.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class ArrangeEntity {
    private Integer id;
    private String name;
    private String description;
    private Date updatedTime;
    private Date createdTime;
}
