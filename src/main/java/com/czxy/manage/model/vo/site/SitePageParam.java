package com.czxy.manage.model.vo.site;

import com.czxy.manage.model.PageParam;
import lombok.Data;

@Data
public class SitePageParam<T>  extends PageParam {
    private String types;
    private String topics;
    private String name;
}
