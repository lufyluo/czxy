package com.czxy.manage.model.vo.site;

import com.czxy.manage.model.entity.BaseEntity;
import com.czxy.manage.model.entity.SiteEntity;
import com.czxy.manage.model.vo.user.UserInfo;
import lombok.Data;

import java.util.List;

@Data
public class SitePageInfo extends SiteEntity {
    private String typeName;
    private Integer typeCategory;
}
