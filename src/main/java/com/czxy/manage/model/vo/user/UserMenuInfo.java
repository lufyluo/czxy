package com.czxy.manage.model.vo.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class UserMenuInfo {
    @ApiModelProperty("menu id")
    private Integer id;
    private Integer parentId;
    private String name;
    private String code;
    private Boolean permission;
    private Date updatedTime;
    private Date createdTime;
}
