package com.czxy.manage.model.vo.subject;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SubjectImportInfo {
    @Excel(name = "课题名称")
    private String name;
    @Excel(name = "课题简述")
    private String content;
    @Excel(name = "授课老师")
    private String teacherName;
    private Integer teacherId;
    @Excel(name = "课题分类",replace = {"党政综合_0","社会管理_1","农业农村_2","城建规划_3","经济与产业_4","能力素质提升_5","其他_9"})
    private Integer category;
}
