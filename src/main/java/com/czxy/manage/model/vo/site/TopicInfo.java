package com.czxy.manage.model.vo.site;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TopicInfo {
    private Integer id;
    @ApiModelProperty("点位ID")
    private Integer siteId;
    @ApiModelProperty("点位课堂名称")
    private String topic;
}
