package com.czxy.manage.model.vo.classes;

import com.czxy.manage.model.PageParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author lufy
 * @Description ...
 * @Date 19-11-18 下午3:50
 */
@Data
public class ClassStudentPageParam<T> extends PageParam<T> {
    @ApiModelProperty("需要排除对班级对学生")
    private Integer classId;
}
