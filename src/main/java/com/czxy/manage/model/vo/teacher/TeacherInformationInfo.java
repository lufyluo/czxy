package com.czxy.manage.model.vo.teacher;

import com.czxy.manage.model.entity.TeacherEntity;
import com.czxy.manage.model.vo.subject.SubjectByIdInfo;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class TeacherInformationInfo extends TeacherEntity {
    private String name;
    private String idCard;
    private String phone;
    @ApiModelProperty("0-男,1-女,2-未知")
    private Integer gender;
    @ApiModelProperty("职位")
    private String position;
    private Integer age;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date birthday;
    @ApiModelProperty("用户类型-0：普通用户，1：学员，2：讲师，3：班主任，4：公职人员，5：其它")
    private Integer category;
    private String wechatId;
    @ApiModelProperty("工作单位")
    private Integer orgId;
    @ApiModelProperty("民族")
    private String nation;
    @ApiModelProperty("籍贯")
    private String nativePlace;
    @ApiModelProperty("学历")
    private String education;
    @ApiModelProperty("政治面貌")
    private String politics;
    private List<SubjectByIdInfo> subjectByIdInfoList;
    @ApiModelProperty("teacher表ID")
    private Integer teacherId;
    @ApiModelProperty("头像")
    private String headImg;
}
