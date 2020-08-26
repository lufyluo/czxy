package com.czxy.manage.model.vo.questionnaire;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class PaperDetailInfo {
    private Integer id;
    private String name;
    private String description;
    @ApiModelProperty("0-为提交，1-已提交")
    private Integer state;
    @ApiModelProperty("用户答案和题目集合")
    private List<StemDetailInfo> stemDetailInfos;
}
