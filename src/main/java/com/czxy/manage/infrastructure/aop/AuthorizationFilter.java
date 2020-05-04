package com.czxy.manage.infrastructure.aop;

import com.alibaba.fastjson.JSON;
import com.czxy.manage.dao.TokenMapper;
import com.czxy.manage.infrastructure.aop.exception.AccountFilterException;
import com.czxy.manage.infrastructure.gloable.ManageException;
import com.czxy.manage.infrastructure.response.ResponseStatus;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.security.auth.message.AuthException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;


@Aspect
@Component
@Slf4j
public class AuthorizationFilter {
    @Pointcut("execution(public * com.czxy.manage.controller..*(..))")
    public void pointExpression() {
    }
    @Autowired
    public HttpServletRequest httpServletRequest;
    @Resource
    public TokenMapper tokenMapper;

    @Around(value = "pointExpression()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        boolean hasAnnotation = hasAnnotation(joinPoint, Anonymous.class);
        if (hasAnnotation) {
            return joinPoint.proceed();
        }
        String token = httpServletRequest.getHeader("token");
        if (StringUtils.isEmpty(token)) {
            return authFail();
        }
        if (tokenMapper.tokenQuery(token)) {
            tokenMapper.flushTime(token);
            return joinPoint.proceed();
        } else {
            return authFail();
        }
    }

    private Object authFail(){
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletResponse response = servletRequestAttributes.getResponse();
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        try {
            response.getWriter().flush();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    private <T extends Annotation> boolean hasAnnotation(JoinPoint joinPoint, Class<T> clazz) {
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        return method.getAnnotation(clazz) != null;
    }
}
