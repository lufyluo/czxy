package com.czxy.manage.model.vo.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author lufy
 * @Description ...
 * @Date 20-4-16 下午9:10
 */
@Data
public class UserCreateInfo extends UserInfo {
    @ApiModelProperty("用户账号")
    private String account;
    @ApiModelProperty("用户密码")
    private String password;
    @ApiModelProperty("工作单位中文名称")
    private String orgName;
}