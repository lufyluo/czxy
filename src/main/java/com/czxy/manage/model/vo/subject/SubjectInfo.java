package com.czxy.manage.model.vo.subject;

import com.czxy.manage.model.entity.BaseEntity;
import com.czxy.manage.model.vo.site.TypeInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class SubjectInfo extends BaseEntity {
    @ApiModelProperty("课题ID")
    private Integer id;
    private String name;
    @ApiModelProperty("课题类型")
    private List<TypeInfo> types;
    @ApiModelProperty("教师ID")
    private Integer teacherId;
    private List<Integer> fileIds;
    @ApiModelProperty("0-党政综合，1-社会管理，2-农业农村，3-城建规划，4-经济与产业，5-能力素质提升，9-其他")
    private Integer category;
    @ApiModelProperty("课题简述")
    private String content;
}
