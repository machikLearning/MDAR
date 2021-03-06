<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/include.jsp" %>

<ul id="lnb">
	<c:choose>
		<c:when test="${param.menuType == 1 }">
			<li class="on"><a href="<c:url value="/admin/ATCManagement"/>">ATC 코드 관리</a></li>
			<li><a href="<c:url value="/admin/medicineManagement"/>">약물 DB 관리</a></li>
			<li><a href="<c:url value="/admin/medicineErrorList"/>">엑셀 오류 약물</a></li>
		</c:when>
		<c:when test="${param.menuType == 2 }">
			<li><a href="<c:url value="/admin/ATCManagement"/>">ATC 코드 관리</a></li>
			<li class="on"><a href="<c:url value="/admin/medicineManagement"/>">약물 DB 관리</a></li>
			<li><a href="<c:url value="/admin/medicineErrorList"/>">엑셀 오류 약물</a></li>
		</c:when>
		<c:otherwise>
			<li><a href="<c:url value="/admin/ATCManagement"/>">ATC 코드 관리</a></li>
			<li><a href="<c:url value="/admin/medicineManagement"/>">약물 DB 관리</a></li>
			<li class="on"><a href="<c:url value="/admin/medicineErrorList"/>">엑셀 오류 약물</a></li>
		</c:otherwise>
	</c:choose>
</ul>