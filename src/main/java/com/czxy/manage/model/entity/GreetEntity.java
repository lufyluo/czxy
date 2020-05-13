package com.czxy.manage.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class GreetEntity {
    private Integer id;
    private String type;
    private String name;
    private String greet;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date sendTime;
}
