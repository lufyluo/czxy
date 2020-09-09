package com.czxy.manage.model.vo.classes;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class ClassWechatInfo {
    @ApiModelProperty("班级ID")
    private Integer id;
    private String name;
    @JsonFormat(pattern = "yyyy年MM月dd日 HH时")
    private Date beginTime;
    @JsonFormat(pattern = "yyyy年MM月dd日 HH时")
    private Date endTime;
    @ApiModelProperty("课程数量")
    private int amount;
    @ApiModelProperty("教学对象")
    private String compositionName;
    @ApiModelProperty("创建时间")
    @JsonFormat(pattern = "yyyy年MM月dd日 HH时")
    private Date createdTime;
    @ApiModelProperty("学生数量")
    private Integer number;
    @ApiModelProperty("互动区开关，0-关闭，1-打开")
    private Byte chatOff;
}
