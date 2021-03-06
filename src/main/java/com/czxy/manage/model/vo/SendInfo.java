package com.czxy.manage.model.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
public class SendInfo {
    @NotBlank
    private String message;
    private List<Integer> userIds;
    private List<Integer> classIds;
    private Integer isToAll;
}
