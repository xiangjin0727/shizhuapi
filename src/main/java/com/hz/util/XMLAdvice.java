package com.hz.util;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;

public class XMLAdvice {
	 
	/**
	 * 在核心业务执行前执行，不能阻止核心业务的调用。
	 * @param joinPoint
	 */
	private void doBefore(JoinPoint joinPoint) {
		System.out.println("-----doBefore().invoke-----");
	}
	
	/**
	 * 手动控制调用核心业务逻辑，以及调用前和调用后的处理,
	 * 
	 * 注意：当核心业务抛异常后，立即退出，转向After Advice
	 * 执行完毕After Advice，再转到Throwing Advice
	 * @param pjp
	 * @return
	 * @throws Throwable
	 */
	private Object doAround(ProceedingJoinPoint pjp) throws Throwable {
		System.out.println("-----doAround().invoke-----");

		//调用核心逻辑
		Object retVal = pjp.proceed();

		System.out.println("-----End of doAround()------");
		return retVal;
	}
 
	/**
	 * 核心业务逻辑退出后（包括正常执行结束和异常退出），执行此Advice
	 * @param joinPoint
	 */
	private void doAfter(JoinPoint joinPoint) {
		System.out.println("-----doAfter().invoke-----");
		System.out.println("-----End of doAfter()------");
	}
	
	/**
	 * 核心业务逻辑调用正常退出后，不管是否有返回值，正常退出后，均执行此Advice
	 * @param joinPoint
	 */
	private void doReturn(JoinPoint joinPoint) {
		System.out.println("-----doReturn().invoke-----");

		System.out.println("-----End of doReturn()------");
	}
	
	/**
	 * 核心业务逻辑调用异常退出后，执行此Advice，处理错误信息
	 * @param joinPoint
	 * @param ex
	 */
	private void doThrowing(JoinPoint joinPoint,Throwable ex) {
		System.out.println("-----doThrowing().invoke-----");

		System.out.println("-----End of doThrowing()------");
	}
}