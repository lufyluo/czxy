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
   @ApiModelProperty("系统：0-党校系统，1-领导干部系统，2-高校系统")
   private Integer system;
   @ApiModelProperty("区域级别：中央、四川省.....")
   private String area;
}
