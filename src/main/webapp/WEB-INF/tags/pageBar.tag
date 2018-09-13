<%@ tag pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ attribute name="pageUrl" required="true" rtexprvalue="true"
	description="分页页面对应的URl"%>
<%@ attribute name="formId" required="true" rtexprvalue="true"
	description="分页页面对应的URl"%>
<%@ attribute name="pageAttrKey" required="true" rtexprvalue="true"
	description="Page对象在Request域中的键名称"%>
<c:set var="pageUrl" value="${pageUrl}" />
<%
	String separator = pageUrl.indexOf("?") > -1 ? "&" : "?";
	jspContext.setAttribute("pageResult",
			request.getAttribute(pageAttrKey));
	jspContext.setAttribute("pageUrl", pageUrl);
	jspContext.setAttribute("formId", formId);
	jspContext.setAttribute("separator", separator);
%>

<div class="total-nb mar-btm">
	<p class="p2">
		<c:if test="${pageResult.page le 1}">
			<a class="shu">首页</a>&nbsp;&nbsp;
	</c:if>
		<c:if test="${pageResult.page gt 1 }">
			<a class="shu" href="javascript:;" onclick="gotoPage('1')">首页</a>&nbsp;&nbsp;
	</c:if>
		<c:if test="${pageResult.hasPrePage}">
			<a class="shu" href="javascript:;"
				onclick="gotoPage('${pageResult.page - 1 }')" class="huang">上一页
			</a>
		</c:if>
		<c:if test="${!pageResult.hasPrePage}">
	  上一页&nbsp;&nbsp;
	</c:if>
	<c:if test="${pageResult.page eq pageResult.totalPages || pageResult.totalPages eq 0}" var="res">
	<a href="javascript:;" onclick="gotoPage('${pageResult.page}')">${pageResult.page}</a>&nbsp; 
	</c:if>
	<c:if test="${!res }">
	
	<c:if  test="${pageResult.page ge 1 and pageResult.page+10 lt pageResult.totalPages}" var="res_1">
	<c:forEach begin="1" end="10" var="pn">
	<c:if test="${pageResult.page%10 eq 0 }" var="result">
	<a href="javascript:;" onclick="gotoPage('<fmt:formatNumber type="number" value="${pageResult.page/10*10 + pn}" maxFractionDigits="0"/>')"><fmt:formatNumber type="number" value="${pageResult.page/10*10 + pn}" maxFractionDigits="0"/></a>&nbsp; 
	</c:if>
	<c:if test="${!result }">
	<a href="javascript:;" onclick="gotoPage('<fmt:formatNumber type="number" value="${pageResult.page - 3 + pn}" maxFractionDigits="0"/>')"><fmt:formatNumber type="number" value="${pageResult.page - 1 + pn}" maxFractionDigits="0"/></a>&nbsp; 
	</c:if>
	</c:forEach>
	</c:if>
	<c:if test="${!res_1 }">
	<c:forEach begin="0" end="${pageResult.totalPages-pageResult.page }" var="pn">
	<a href="javascript:;" onclick="gotoPage('<fmt:formatNumber type="number" value="${pageResult.page + pn}" maxFractionDigits="0"/>')"><fmt:formatNumber type="number" value="${pageResult.page + pn}" maxFractionDigits="0"/></a>&nbsp; 
	</c:forEach>
	</c:if>
	
	</c:if>
		
		<c:if test="${pageResult.hasNextPage}">
			<a class="shu" href="javascript:;"
				onclick="gotoPage('${pageResult.page+1 }')" class="huang">下一页</a>&nbsp;&nbsp; 
	</c:if>
		<c:if test="${!pageResult.hasNextPage}">
	  下一页&nbsp;&nbsp;
	</c:if>
		当前<span class="huang">${pageResult.page}</span>
		/${pageResult.totalPages}&nbsp;&nbsp; <a class="shu" href="#">共${pageResult.totalCount}条</a>
		<span>转到 <input class="shuru" type="text" id="go"  value="${pageResult.page}"/>
			&ensp;页&emsp; <input type="hidden" id="pageNo" name="page" value="${pageResult.page}"> <input
			type="button" value="确定" class="total-input" />&emsp;
		</span>
	</p>
</div>
<script type="text/javascript">
<!--
	//-->
	function gotoPage(pageNo) {
		$("#pageNo").val(pageNo);
		var totalPages = "${pageResult.totalPages}";
		if(pageNo>totalPages){
			$("#pageNo").val(totalPages);
		}
		//$("#${formId}").attr("action","${pageUrl}");
		$("#${formId}").submit();
	}
	$(document).ready(function() {
		$(".total-input").click(function() {
			if ($("#go").val() == '' || $("#go").val() <= 0) {
				alert("请输入有效页码！");
			} else {
				gotoPage($("#go").val());
			}

		});
	})
</script>

