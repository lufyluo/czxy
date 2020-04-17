package com.czxy.manage.infrastructure.aop;

import com.czxy.manage.dao.TokenMapper;
import com.czxy.manage.infrastructure.aop.exception.AccountFilterException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;


@Aspect
@Component
public class AuthorizationFilter {
    @Pointcut("execution(public * com.czxy.manage.controller..*(..))")
    public void pointExpression() {
    }
    @Autowired
    public HttpServletRequest httpServletRequest;
    @Resource
    public TokenMapper tokenMapper;

    @Before(value = "pointExpression()")
    public void before(JoinPoint joinPoint) {
        boolean hasAnnotation = hasAnnotation(joinPoint, Anonymous.class);
        if (hasAnnotation) {
            return;
        }
        String token = httpServletRequest.getHeader("token");
        if (StringUtils.isEmpty(token)) {
            throw new AccountFilterException("鉴权失败！");
        }
        if (tokenMapper.tokenQuery(token)) {
            return;
        } else {
            throw new AccountFilterException("鉴权失败！");
        }

    }
    private <T extends Annotation> boolean hasAnnotation(JoinPoint joinPoint, Class<T> clazz) {
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        return method.getAnnotation(clazz) != null;
    }
}
