package com.czxy.manage.model.vo.classes.files;

import com.czxy.manage.model.PageParam;
import lombok.Data;

@Data
public class ClassFilePageParam<T> extends PageParam<T> {
    private Integer classId;
}
