package com.hz.app.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hz.app.user.service.TService;
import com.hz.app.user.service.UserMyInformationService;
import com.hz.util.TestAnnotation;

@Controller
@RequestMapping("test")
public class TestController implements TService {
	
	@Autowired
	UserMyInformationService userMyInformationService;
	
	@TestAnnotation(requestUrl = "aop")
	@RequestMapping(value="aop")
	@ResponseBody
	public void testAop(){
		System.err.println("-------------------------------");
		//userMyInformationService.test();
	}
}
