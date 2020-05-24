package com.czxy.manage.model.entity;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
public class SendEntity {
    private Long id;
    private String message;
    private Integer isToAll;
    private Integer userId;
}
