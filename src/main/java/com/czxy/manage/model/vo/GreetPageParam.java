package com.czxy.manage.model.vo;

import com.czxy.manage.model.PageParam;
import lombok.Data;

@Data
public class GreetPageParam<T> extends PageParam<T> {
    private String type;
}
