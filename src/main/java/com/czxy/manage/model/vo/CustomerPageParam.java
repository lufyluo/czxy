package com.czxy.manage.model.vo;

import com.czxy.manage.model.PageParam;
import lombok.Data;

@Data
public class CustomerPageParam<T> extends PageParam<T> {
    private Integer star;
}
