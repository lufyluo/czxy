package com.czxy.manage.model.entity;

import com.czxy.manage.model.vo.user.UserInfo;
import lombok.Data;

@Data
public class StudentAddEntity extends UserInfo {
    private String orgName;
    private Integer classId;
    private Integer type;
    private Integer signFlag;
    private Integer userId;
}
