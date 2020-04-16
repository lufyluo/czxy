package com.czxy.manage.infrastructure.gloable.filter;

import com.czxy.manage.infrastructure.gloable.ManageException;
import com.czxy.manage.infrastructure.response.BaseResponse;
import com.czxy.manage.infrastructure.response.ResponseStatus;
import com.czxy.manage.infrastructure.response.ResponseUtil;
import org.springframework.util.StringUtils;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @Author lufy
 * @Description ...
 * @Date 19-8-26 上午11:29
 */
@ControllerAdvice
public class ErrorController {
    @ExceptionHandler(ManageException.class)
    @ResponseBody
    public BaseResponse invoke(ManageException ex) {
        if (StringUtils.isEmpty(ex.getDetails())) {
            return ResponseUtil.failure(ex.getStatus());
        }
        return ResponseUtil.failure(ex.getDetails(), ex.getStatus(), null);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public BaseResponse validExceptionHandler(MethodArgumentNotValidException e) {
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        StringBuilder stringBuilder = new StringBuilder();
        for (FieldError error : fieldErrors) {
            if (!StringUtils.isEmpty(stringBuilder.toString())) {
                stringBuilder.append("; ");
            }
            stringBuilder.append(error.getField());
            stringBuilder.append(": ");
            stringBuilder.append(error.getDefaultMessage());
        }
        String msg = stringBuilder.toString();
        return ResponseUtil.failure(msg, ResponseStatus.ARGUMENTNOTVALID, null);
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public BaseResponse invoke(Exception ex) {
        return ResponseUtil.failure(ex.getMessage());
    }
}
