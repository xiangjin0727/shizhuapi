package com.hz.util;
import org.aspectj.lang.annotation.Pointcut;
public class Test1 {

	    @Pointcut(value = "within(com.hz.app.user.controller.TestController.*)")
	    public void aopDemo() {

	    }
}
