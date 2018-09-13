<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/pub/jsp/taglibs.jsp"%>

<style type="text/css">
#pageInfo>a {
	cursor:pointer;
	padding-left:10px;
}
</style>

<input type="hidden" id="page" name="page" value="${pageView.page }" />
	<p id="pageInfo" class="p2"> 
		共<span class="huang" style="padding-left: 4px; padding-right: 4px;">${pageView.totalCount}</span>条
		<span class="huang" style="padding-right: 4px;">${pageView.totalPages}</span>页
		<span style="padding-right: 24px;"></span>
<c:if test="${pageView.totalPages>1 }">
		<c:choose>
			<c:when test="${pageView.page>1}">
				<a title="1" onclick="topage(1)">首页</a>
				<a title="${pageView.prePage }"
					onclick="topage('${pageView.prePage}')">上一页</a>
			</c:when>
		</c:choose>
		<c:forEach begin="${pageView.pageIndex.startIndex}"
			end="${pageView.pageIndex.endIndex}" var="wp">
			<a title="${wp }" onclick="topage('${wp}')"
				<c:if test="${pageView.page==wp}">style="color:red"</c:if>>${wp}</a>
		</c:forEach>
		<c:choose>
			<c:when test="${pageView.page<pageView.totalPages}">
				<a title="${pageView.nextPage }"
					onclick="topage('${pageView.nextPage}')">下一页</a>
				<a title="${pageView.totalPages }"
					onclick="topage('${pageView.totalPages}')">末页</a>
			</c:when>
		</c:choose>
</c:if>
	</p>