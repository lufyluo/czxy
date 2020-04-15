package com.czxy.manage.infrastructure.util.log;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author lufy
 * @Description ...
 * @Date 19-8-20 下午4:26
 */
@Aspect
@Order(4)
@Component
@Slf4j
public class LogAspect {

    @Value("${logstrategy.request}")
    private boolean needRequestLog;

    @Pointcut("execution(public * com.czxy.manage.*.controller..*(..))")
    public void declearJoinPointExpression() {
    }

    @Around(value = "declearJoinPointExpression()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        String args = JSON.toJSONString(joinPoint.getArgs());
        try {
            StopWatch stopWatch = new StopWatch();
            stopWatch.start();
            Object result = joinPoint.proceed();
            stopWatch.stop();
            if (needRequestLog) {
                log.info(buildLog(args, result));
            }
            return result;
        } catch (Throwable throwable) {
            log.error(buildLog(args, throwable.getMessage()));
            throw throwable;
        }

    }

    private String buildLog(String params, Object response) {
        try {
            RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
            HttpServletRequest httpServletRequest = ((ServletRequestAttributes) requestAttributes).getRequest();
            RestLog restLog = new RestLog();
            restLog.setRequestBody(params);
            restLog.setMethod(httpServletRequest.getMethod());
            restLog.setUrl(httpServletRequest.getServletPath());
            restLog.setRequestBody(response);
            restLog.setRemoteIp(httpServletRequest.getRemoteAddr());
            return JSON.toJSONString(restLog);
        } catch (Exception e) {
            log.error(e.getMessage());
            return String.format("日志统一记录出现错误： %s", e.getMessage());
        }

    }
}
