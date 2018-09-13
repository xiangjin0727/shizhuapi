package com.hz.util;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class Test2 {

    @Before(value = "com.hz.util.Test1.aopDemo()")
    public void before(JoinPoint joinPoint) {
        System.out.println("[Aspect1] before advise");
    }

    @Around(value = "com.hz.util.Test1.aopDemo()")
    public void around(ProceedingJoinPoint pjp) throws  Throwable{
        System.out.println("[Aspect1] around advise 1");
        pjp.proceed();
        System.out.println("[Aspect1] around advise2");
    }

    @AfterReturning(value = "com.hz.util.Test1.aopDemo()")
    public void afterReturning(JoinPoint joinPoint) {
        System.out.println("[Aspect1] afterReturning advise");
    }

    @AfterThrowing(value = "com.hz.util.Test1.aopDemo()")
    public void afterThrowing(JoinPoint joinPoint) {
        System.out.println("[Aspect1] afterThrowing advise");
    }

    @After(value = "com.hz.util.Test1.aopDemo()")
    public void after(JoinPoint joinPoint) {
        System.out.println("[Aspect1] after advise");
    }
}
