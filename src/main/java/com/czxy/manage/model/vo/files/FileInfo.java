package com.czxy.manage.model.vo.files;

import lombok.Data;

@Data
public class FileInfo {
    private Integer id;
    private String name;
    private String code;
    private String extension;
    private Long size;
    private String url;
}
