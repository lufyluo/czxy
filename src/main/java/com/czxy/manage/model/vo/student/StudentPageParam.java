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
public class StudentPageParam<T> extends PageParam {
   private Integer provinceId;
   private Integer city;
   private Integer county;
   private Integer classId;
}
