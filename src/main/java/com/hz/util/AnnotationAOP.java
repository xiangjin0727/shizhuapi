package com.hz.util;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @author sam
 * @since 2017/7/13
 */
//@Aspect //AOP 切面
@Component
public class AnnotationAOP {

	 
	    //切入点
	    @Pointcut(value = "@annotation(com.sam.annotation.com.hz.util.TestAnnotation)")
	    private void pointcut() {
	 
	    }
	 
	 
	    /**
	     * 在方法执行前后
	     *
	     * @param point
	     * @param myLog
	     * @return
	     */
	    @Around(value = "pointcut() && @annotation(TestAnnotation)")
	    public Object around(ProceedingJoinPoint point, TestAnnotation TestAnnotation) {
	 
	        System.out.println("++++执行了around方法++++");
	 
	        String requestUrl = TestAnnotation.requestUrl();
	 
	        //拦截的类名
	        Class clazz = point.getTarget().getClass();
	        //拦截的方法
	        Method method = ((MethodSignature) point.getSignature()).getMethod();
	 
	        System.out.println("执行了 类:" + clazz + " 方法:" + method + " 自定义请求地址:" + requestUrl);
	 
	        try {
	            return point.proceed(); //执行程序
	        } catch (Throwable throwable) {
	            throwable.printStackTrace();
	            return throwable.getMessage();
	        }
	    }
	 
	    /**
	     * 方法执行后
	     *
	     * @param joinPoint
	     * @param myLog
	     * @param result
	     * @return
	     */
	    @AfterReturning(value = "pointcut() && @annotation(TestAnnotation)", returning = "result")
	    public Object afterReturning(JoinPoint joinPoint, TestAnnotation testAnnotation, Object result) {
	 
//	        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
//	        HttpSession session = request.getSession();
	 
	        System.out.println("++++执行了afterReturning方法++++");
	 
	        System.out.println("执行结果：" + result);
	 
	        return result;
	    }
	 
	    /**
	     * 方法执行后 并抛出异常
	     *
	     * @param joinPoint
	     * @param myLog
	     * @param ex
	     */
	    @AfterThrowing(value = "pointcut() && @annotation(TestAnnotation)", throwing = "ex")
	    public void afterThrowing(JoinPoint joinPoint, TestAnnotation testAnnotation, Exception ex) {
	        System.out.println("++++执行了afterThrowing方法++++");
	        System.out.println("请求：" + testAnnotation.requestUrl() + " 出现异常");
	    }
	 

}
