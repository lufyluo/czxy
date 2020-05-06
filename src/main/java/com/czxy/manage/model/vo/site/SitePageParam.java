package com.czxy.manage.model.vo.site;

import com.czxy.manage.model.PageParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SitePageParam<T>  extends PageParam<T> {
    @ApiModelProperty("点位类型id")
    private String typeId;
}
