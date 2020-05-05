package com.czxy.manage.model.vo.subject;

import com.czxy.manage.model.PageParam;
import lombok.Data;

@Data
public class SubjectPageParam<T> extends PageParam<T> {
    private Integer typeId;
    private Integer category;
}
