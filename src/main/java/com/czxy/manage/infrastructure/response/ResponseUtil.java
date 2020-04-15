package com.czxy.manage.infrastructure.response;

import org.springframework.util.StringUtils;

/**
 * @Author lufy
 * @Description ...
 * @Date 19-11-13 下午2:14
 */
public class ResponseUtil {
    public static <T> BaseResponse<T> success(T data) {
        return new BaseResponse(ResponseStatus.SUCCESS, data);
    }

    public static <T> BaseResponse<T> failure(T data) {
        return new BaseResponse(ResponseStatus.FAILURE, data);
    }

    public static <T> BaseResponse<T> failure(String msg, T data) {
        if (StringUtils.isEmpty(msg)) {
            return new BaseResponse(ResponseStatus.FAILURE, data);
        }
        return new BaseResponse(ResponseStatus.FAILURE, data, msg);
    }

    public static <T> BaseResponse<T> failure(String msg, ResponseStatus status, T data) {
        if (StringUtils.isEmpty(msg)) {
            return new BaseResponse(status, data);
        }
        return new BaseResponse(status, data, msg);
    }
}
