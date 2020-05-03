package com.czxy.manage.model.vo.teacher;

import com.czxy.manage.model.PageParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author lufy
 * @Description ...
 * @Date 19-11-18 下午3:50
 */
@Data
public class TeacherPageParam<T> extends PageParam<T> {
   @ApiModelProperty("职称：高级、一级、二级、三级")
   private String skill;
   @ApiModelProperty("星级：一级、二级、三级")
   private String star;
}
