package com.czxy.manage.model.vo.questionnaire;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class PaperPublisInfo {
    @ApiModelProperty("是否推送给所有学员")
    private Integer isToAll;
    @ApiModelProperty("省id")
    private Integer provinceId;
    @ApiModelProperty("市id")
    private Integer cityId;
    @ApiModelProperty("区、县id")
    private Integer countyId;
    private List<Integer> userIds;
    private List<Integer> classIds;
    private Integer paperId;
}
