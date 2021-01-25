package com.czxy.manage.model.vo.files;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class FileInfo {
    private Integer id;
    private String name;
    private String code;
    private String extension;
    private Long size;
    private String url;
    @JsonFormat(pattern = "yyyy年MM月dd日 HH:mm:ss")
    private Date createdTime;
    private Boolean canPreview;
}
