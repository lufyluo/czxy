package com.czxy.manage.model.vo.customer;

import com.czxy.manage.model.PageParam;
import lombok.Data;

@Data
public class CustomerPageParam<T> extends PageParam<T> {
    private Integer star;
}
