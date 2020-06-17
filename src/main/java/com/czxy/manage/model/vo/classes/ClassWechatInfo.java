package com.czxy.manage.model.vo.classes;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class ClassWechatInfo {
    @ApiModelProperty("班级ID")
    private Integer id;
    private String name;
    private Date beginTime;
    private Date endTime;
    @ApiModelProperty("课程数量")
    private int amount;
    @ApiModelProperty("教学对象")
    private String compositionName;
    @ApiModelProperty("创建时间")
    private Date createdTime;
}
