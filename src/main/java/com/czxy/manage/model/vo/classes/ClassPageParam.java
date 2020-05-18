package com.czxy.manage.model.vo.classes;

import com.czxy.manage.model.PageParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ClassPageParam<T> extends PageParam<T> {
    @ApiModelProperty("可选参数，班级成分、培训对象、班级类型Id")
    private Integer compositionId;
}
