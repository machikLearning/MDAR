<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/include.jsp" %>

<!-- nav :s -->
<nav>
   <c:choose>
		<c:when test="${param.menuType == 1 }">
			<ul id="nav_wrap">
				<li><a href="<c:url value="/patient/main"/>" class="ov">의약품 안전카드</a></li>
				<li><a href="<c:url value="/patient/searchMedicine"/>"> 약품 검색</a></li>
			</ul>
		</c:when>
		<c:otherwise>
			<ul id="nav_wrap">
				<li><a href="<c:url value="/patient/main"/>">의약품 안전카드</a></li>
				<li><a href="<c:url value="/patient/searchMedicine"/>" class="ov"> 약품 검색</a></li>
			</ul>
		</c:otherwise>
	</c:choose>
</nav>
<!-- nav :e -->
