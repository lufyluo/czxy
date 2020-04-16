package com.czxy.manage.infrastructure.gloable;

import com.czxy.manage.infrastructure.response.ResponseStatus;

/**
 * @Author lufy
 * @Description ...
 * @Date 20-4-16 下午9:57
 */
public class ManageException extends RuntimeException {
    private static final long serialVersionUID = -4742832112872227456L;
    /**
     * 系统错误码
     */
    private ResponseStatus status;

    private String details;

    public ManageException() {
        super();
    }

    public ManageException(Throwable t) {
        super(t);
    }

    public ManageException(ResponseStatus status, String details) {
        super(details);
        this.status = status==null?ResponseStatus.FAILURE:status;
        this.details = details;
    }

    public ManageException(ResponseStatus status) {
        super(status.getDesc());
        this.status = status;
        this.details = status.getDesc();
    }

    public ResponseStatus getStatus() {
        return status;
    }

    public String getDetails() {
        return details;
    }
}
