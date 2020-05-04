package com.czxy.manage.model.vo.user;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import java.util.List;

@Getter
@Setter
public class UserMenuSaveInfo {
    @Min(value = 1,message = "用户id错误")
    private Integer userId;
    private List<String> codes;
}
