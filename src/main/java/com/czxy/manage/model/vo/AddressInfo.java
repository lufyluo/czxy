package com.czxy.manage.model.vo;

import lombok.Data;

@Data
public class AddressInfo {
    private Integer id;
    private String name;
    private Integer parentId;
    private Integer category;
    private String code;
    private String sname;
    private String cityCode;
    private String path;
    private String pinyin;
}
