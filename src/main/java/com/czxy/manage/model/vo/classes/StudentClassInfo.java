package com.czxy.manage.model.vo.classes;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class StudentClassInfo {
    private Integer classId;
    private List<Integer> studentIds;
}
