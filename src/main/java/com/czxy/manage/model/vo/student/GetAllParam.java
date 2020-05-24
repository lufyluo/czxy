package com.czxy.manage.model.vo.student;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class GetAllParam {
    private Integer isToAll;
    private Integer provinceId;
    private Integer cityId;
    private Integer countyId;
    private List<Integer> userIds;
    private List<Integer> classIds;
    private Integer paperId;
}
