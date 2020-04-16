package com.czxy.manage.model.vo.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @Author lufy
 * @Description ...
 * @Date 19-8-28 上午9:39
 */
@Data
@ApiModel("用户账号")
public class AccountInfo implements Serializable {
    @JsonIgnore
    private int id;
    @NotBlank(message = "账号密码错误！")
    @ApiModelProperty("账号")
    private String account;
    @NotBlank(message = "账号密码错误！")
    @ApiModelProperty("密码")
    private String password;
}
