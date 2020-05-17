package com.czxy.manage.model.vo.questionnaire;

import com.czxy.manage.model.vo.PaperAddInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class PaperCreateInfo {
    private Integer id;
    private String name;
    private String description;
    @ApiModelProperty("题目集合")
    private List<PaperAddInfo> paperAddInfos;
}
