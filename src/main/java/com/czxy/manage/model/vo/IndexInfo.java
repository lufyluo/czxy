package com.czxy.manage.model.vo;

import com.czxy.manage.model.vo.message.MessageInfo;
import lombok.Data;

import java.util.List;

@Data
public class IndexInfo {
    private MessageInfo message;
    private List<String> url;
}
