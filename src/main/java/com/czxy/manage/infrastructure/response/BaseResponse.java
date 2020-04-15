package com.czxy.manage.infrastructure.response;


import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;

import java.io.Serializable;

/**
 * @Author lufy
 * @Description ...
 * @Date 19-8-14 下午5:22
 */
@Getter
public class BaseResponse<T> implements Serializable {
    protected String code;
    protected String msg;
    protected T data;
    @JsonIgnore
    @ApiModelProperty(hidden = true)
    protected ResponseStatus status;

    public BaseResponse() {
    }

    public BaseResponse(ResponseStatus status) {
        this.code = status.getCode();
        this.msg = status.getDesc();
        this.status = status;
    }

    public BaseResponse(ResponseStatus status, String msg) {
        this.code = status.getCode();
        this.msg = msg;
        this.status = status;
    }

    public BaseResponse(ResponseStatus status, T data) {
        this.code = status.getCode();
        this.msg = status.getDesc();
        this.status = status;
        this.data = data;
    }

    public BaseResponse(ResponseStatus status, T data, String msg) {
        this.code = status.getCode();
        this.msg = msg;
        this.status = status;
        this.data = data;
    }


    public BaseResponse<T> setMsg(String msg) {
        this.msg = msg;
        return this;
    }

}
