package com.czxy.manage.model.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class StudentClassNameEntity {
    private Integer id;
    private String name;
    private String idcard;
    private String phone;
    private Integer gender;
    @ApiModelProperty("职务")
    private String position;
    private Integer age;
    private Date birthday;
    @ApiModelProperty("用户类型-0：普通用户，1：学员，2：讲师，3：班主任，4：公职人员，5：其它")
    private Integer category;
    private String wechatId;
    private Integer orgId;
    @ApiModelProperty("民族")
    private String nation;
    @ApiModelProperty("籍贯")
    private String nativePlace;
    @ApiModelProperty("学历")
    private String education;
    @ApiModelProperty("政治面貌")
    private String politics;
    private String className;
    private Date beginTime;
    private Date endTime;
    private String classState;
}
