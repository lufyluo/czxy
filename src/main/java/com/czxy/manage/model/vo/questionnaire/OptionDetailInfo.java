package com.czxy.manage.model.vo.questionnaire;

import com.czxy.manage.model.vo.questionnaire.stem.OptionInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class OptionDetailInfo extends OptionInfo {
    @ApiModelProperty("是否选中")
    private Boolean selected;
}
