package com.czxy.manage.model.vo.teacher;

import com.czxy.manage.model.entity.BaseEntity;
import com.czxy.manage.model.entity.TeacherEntity;
import com.czxy.manage.model.entity.UserEntity;
import com.czxy.manage.model.vo.user.UserInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class TeacherInfo extends UserInfo {
    private Integer score;
    private String comment;
    private Integer userId;
    private Integer system;
    private String skill;
    private String star;
    private String area;
    private String orgName;
}
