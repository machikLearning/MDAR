<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/include.jsp" %>

<ul id="lnb">
	<c:choose>
		<c:when test="${param.menuType == 1 }">
			<li class="on"><a href="<c:url value="/admin/hospitalManagement"/>">참여병원관리</a></li>
			<li><a href="<c:url value="/admin/prescripitionTemplateMain"/>">처방탬플릿관리</a></li>
		</c:when>
		<c:otherwise>
			<li><a href="<c:url value="/admin/hospitalManagement"/>">참여병원관리</a></li>
			<li class="on"><a href="<c:url value="/admin/prescripitionTemplateMain"/>">처방탬플릿관리</a></li>
		</c:otherwise>
	</c:choose>
</ul>