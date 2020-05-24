package com.czxy.manage.model.vo.classes;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class ClassStudentInfo {
    private Integer userId;
    private Integer studentId;
    private String userName;
    private String userIdCard;
    private String userPhone;
    @ApiModelProperty("0-男,1-女,2-未知")
    private Integer userGender;
    @ApiModelProperty("职位")
    private String userPosition;
    private Integer userAge;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date userBirthday;
    @ApiModelProperty("用户类型-0：普通用户，1：学员，2：讲师，3：班主任，4：公职人员，5：其它")
    private Integer userCategory;
    private String userWechatId;
    @ApiModelProperty("工作单位唯一标识")
    private Integer userOrgId;
    @ApiModelProperty("工作单位")
    private Integer userOrgName;
    @ApiModelProperty("民族")
    private String userNation;
    @ApiModelProperty("籍贯")
    private String userNativePlace;
    @ApiModelProperty("学员身份")
    private Integer studentType;
    private String studentTypeName;
}
