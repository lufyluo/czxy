package com.czxy.manage.model.vo.message;

import com.czxy.manage.model.PageParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;

/**
 * @Author lufy
 * @Description ...
 * @Date 19-11-18 下午3:50
 */
@Data
public class UserPageParam<T> extends PageParam<T> {
    @ApiModelProperty("用户id")
    @Min(value = 1,message = "用户id错误")
    private Integer userId;
}
