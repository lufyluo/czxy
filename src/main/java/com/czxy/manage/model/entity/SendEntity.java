package com.czxy.manage.model.entity;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
public class SendEntity {
    private Integer id;
    @NotBlank
    private String message;
    private List<Integer> userIds;
    private List<Integer> classIds;
    private Integer isToAll;
    private String userIdsString;
    private String classIdsString;
}
