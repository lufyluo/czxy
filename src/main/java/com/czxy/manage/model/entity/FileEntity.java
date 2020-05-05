package com.czxy.manage.model.entity;

import lombok.Data;

@Data
public class FileEntity extends BaseEntity{
    private Integer id;
    private String name;
    private String code;
    private String extension;
    private Long size;
    private String url;
}
