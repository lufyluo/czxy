package com.czxy.manage.model.vo.classes;

import lombok.Data;

@Data
public class ClassOrgInfo extends ClassInfo{
    private String orgName;
    private String topicNames;
    private String masterName;
    private String leaderName;
}
