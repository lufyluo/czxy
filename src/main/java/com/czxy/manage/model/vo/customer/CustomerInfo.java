package com.czxy.manage.model.vo.customer;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CustomerInfo {
    private Integer id;
    @ApiModelProperty("对接人姓名")
    private String contactorName;
    @ApiModelProperty("单位名称，客户名称")
    private String orgName;
    @ApiModelProperty("对接人职务职位")
    private String contactorPosition;
    @ApiModelProperty("对接人电话")
    private String contactorPhone;
    @ApiModelProperty("班级名称")
    private String className;
    @ApiModelProperty("客户星级")
    private Integer orgStar;
    private Integer orgId;
}
