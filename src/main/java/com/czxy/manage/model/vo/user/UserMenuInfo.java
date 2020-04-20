package com.czxy.manage.model.vo.user;

import lombok.Data;

import java.util.Date;

@Data
public class UserMenuInfo {
    private Integer id;
    private Integer parentId;
    private String name;
    private String code;
    private Date updatedTime;
    private Date createdTime;
}
