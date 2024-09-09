package com.yoyo.common.kafka;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    private final LoggingProducer producer;

    public LoggingAspect(LoggingProducer loggingProducer) {
        this.producer = loggingProducer;
    }
    @Before("@annotation(com.yoyo.common.Loggable)")
    public void beforeMethodExecution(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        producer.sendMessage("logging", "Before executing method:" + methodName);
    }
}
