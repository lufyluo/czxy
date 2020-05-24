package com.czxy.manage.model.vo.questionnaire;

import com.czxy.manage.model.PageParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PaperPageParam<T> extends PageParam<T> {
    @ApiModelProperty("用户ID")
    private Integer userId;

}
