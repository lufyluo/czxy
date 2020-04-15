package com.czxy.manage.infrastructure.util.log;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @Author lufy
 * @Description ...
 * @Date 19-10-12 下午3:02
 */
@Getter
@Setter
public class RestLog<reqT, repT> implements Serializable {
    @ApiModelProperty("请求方式（post，get，put，delete）")
    private String method;
    private String url;
    private String token;
    private Long useTime;
    private String remoteIp;
    private reqT requestBody;
    private repT response;
}
