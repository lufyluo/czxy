package com.czxy.manage.model.vo.student;

import com.czxy.manage.model.PageParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author lufy
 * @Description ...
 * @Date 19-11-18 下午3:50
 */
@Data
public class StudentPageParam<T> extends PageParam<T> {
   @ApiModelProperty("省ID")
   private Integer provinceId;
   @ApiModelProperty("市ID")
   private Integer cityId;
   @ApiModelProperty("镇ID")
   private Integer countyId;
   private Integer classId;
   @ApiModelProperty("排除班级的学生所在班级id")
   private Integer excludeClassId;
}
