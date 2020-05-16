package com.czxy.manage.model.vo.plan;

import com.czxy.manage.model.PageParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PlanPageParam<T> extends PageParam<T> {
    @ApiModelProperty("类型id")
    private Integer typeId;
}
