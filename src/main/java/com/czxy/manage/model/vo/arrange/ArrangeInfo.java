package com.czxy.manage.model.vo.arrange;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class ArrangeInfo {
    @ApiModelProperty("课表ID")
    private Integer id;
    private String name;
    @ApiModelProperty("课表描述")
    private String description;
    @ApiModelProperty("课表状态")
    private String stateDesc;
    @ApiModelProperty("0-未使用，1-使用中")
    private Integer state;
    private Date beginTime;
    private Date endTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updatedTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdTime;
}
