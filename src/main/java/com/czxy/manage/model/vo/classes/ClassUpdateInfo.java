package com.czxy.manage.model.vo.classes;

import com.czxy.manage.model.vo.student.StudentAddInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class ClassUpdateInfo extends ClassOrgInfo{
    @ApiModelProperty("班主任用户id")
    private Integer masterId;
    @ApiModelProperty("课程表id")
    private Integer classArrangeId;
    @ApiModelProperty("培训对象(班级成分id)")
    private Integer compositionId;
    private List<StudentAddInfo> studentAddInfos;
}
