<%@ page language="java" contentType="application/xml; charset=GBK"
    pageEncoding="UTF-8"%><%@ taglib prefix="s" uri="/struts-tags"%><%
	//debug
    //System.out.println("responseText="+request.getAttribute("responseText"));
%><%
	String xml = (String)request.getAttribute("responseText");
	if(null==xml || xml.indexOf("<?xml")<0){
		out.println("<?xml version=\"1.0\" encoding=\"GBK\"?>");
		//debug
		//System.out.println("debug: no xml head");
	}
%><s:property value="responseText" escape="false"/>