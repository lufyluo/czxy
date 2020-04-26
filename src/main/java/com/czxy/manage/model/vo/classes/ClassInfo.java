package com.czxy.manage.model.vo.classes;

import com.czxy.manage.model.entity.BaseEntity;
import lombok.Data;

import java.util.Date;

@Data
public class ClassInfo extends BaseEntity {
    private Integer id;
    private String name;
    private Integer addressId;
    private Integer orgId;
    private Date beginTime;
    private Date endTime;
    private Integer arrangeId;
    private String topics;
    private Integer recommendOrgId;
    private Integer compositionId;
    private Integer duration;
}
