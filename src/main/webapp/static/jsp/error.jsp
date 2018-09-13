<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.lang.Exception" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	Exception e = (Exception)request.getAttribute("exception");
	e.printStackTrace();
	out.print("{success:false,msg:'出现错误: "+e.getMessage()+"<br/><br/> 详细信息是: "+e+"'}");
%>
