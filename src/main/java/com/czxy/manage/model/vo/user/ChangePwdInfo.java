package com.czxy.manage.model.vo.user;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @Author lufy
 * @Description ...
 * @Date 19-12-17 上午11:05
 */
@Data
public class ChangePwdInfo implements Serializable {
    @NotBlank(message = "原密码错误")
    private String originPassword;
    @NotBlank(message = "新密码不能为空")
    private String newPassword;
}
