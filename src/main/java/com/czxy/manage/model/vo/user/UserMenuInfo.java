package com.czxy.manage.model.vo.user;

import com.czxy.manage.infrastructure.util.maplain.Ancestors;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class UserMenuInfo extends Ancestors {
    private String name;
    private String code;
    private Boolean permission;
    private Date updatedTime;
    private Date createdTime;
}
