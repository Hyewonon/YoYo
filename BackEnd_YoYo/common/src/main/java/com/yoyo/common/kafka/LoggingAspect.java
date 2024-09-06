//package com.yoyo.common.kafka;
//
//import lombok.RequiredArgsConstructor;
//import org.aspectj.lang.JoinPoint;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Before;
//import org.springframework.stereotype.Component;
//
//@Aspect
//@Component
//@RequiredArgsConstructor
//public class LoggingAspect {
//
//    private final LoggingProducer producer;
//
//    @Before("execution(* com.yoyo.*.controller)")
//    public void beforeMethodExecution(JoinPoint joinPoint) {
//        String methodName = joinPoint.getSignature().getName();
//        producer.sendMessage("logging", "Before executing method: " + methodName );
//    }
//}
