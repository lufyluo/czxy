package com.czxy.manage.model.vo.user;

import com.czxy.manage.infrastructure.util.maplain.Ancestors;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class UserMenuInfo extends Ancestors {
    @ApiModelProperty("菜单名称")
    private String name;
    @ApiModelProperty("菜单code")
    private String code;
    @ApiModelProperty("是否有权限")
    private Boolean permission;
    private String url;
    private String icon;
    private Date updatedTime;
    private Date createdTime;
}
