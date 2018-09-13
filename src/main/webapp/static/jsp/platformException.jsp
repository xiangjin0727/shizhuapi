<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.lang.Exception" %>
<%@ include file="/pub/jsp/taglibs.jsp"%>
<%@ include file="/pub/jsp/meta.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<HTML>
<HEAD>
<TITLE>网站后台管理系统</TITLE>
<script type="text/javascript" src="${ctx}/pub/js/frame.js"></script>
<%
	Exception e = (Exception) request.getAttribute("exception");
	e.printStackTrace();
%>
<script type="text/javascript">
$(function(){
	var msg = '<%= e.getMessage() %>';
	MsgBox(msg);
})
</script>
</HEAD>
<BODY>
</BODY>
</HTML>
