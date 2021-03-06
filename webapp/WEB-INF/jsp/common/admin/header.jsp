<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/include.jsp" %>
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
<script>
	$(document).ready(function(){
		<c:set var="loginUser" value="${user }"/>
			<c:if test="${loginUser==null }">
				alert("로그인이 필요한 페이지 입니다.");
			</c:if>
	});
</script>
<div id="header">
	
	<h1 id="logo"><a href="<c:url value="/main/selectFunc"/>"><img src="<c:url value="/img/layout/logo.png"/>" alt="충북대학교 병원" /></a></h1>
	
	<!-- gnbWrap :s -->
	<div id="gnbWrap">	
		<div id="gnb">
			<ul class="menu">
				<li><a href="<c:url value="/admin/accountManagement"/>">사용자 관리</a>
					<ul class="sub">
						<li><a href="<c:url value="/admin/accountManagement"/>">계정 관리</a></li>
						<li><a href="<c:url value="/admin/doctorApprovalManagement"/>">의사 요청 승인</a></li>
					</ul>
				</li>
				<!-- 
				<li><a href="<c:url value="/admin/ATCManagement"/>">약물DB관리</a>
					<ul class="sub">
						<li><a href="<c:url value="/admin/ATCManagement"/>">ATC코드 관리</a></li>
						<li><a href="<c:url value="/admin/medicineManagement"/>">약물 DB 관리</a></li>
						<li><a href="<c:url value="/admin/medicineErrorList"/>">오류 약물 DB 관리</a></li>
					</ul>
				</li>
				 -->
				<li><a href="<c:url value="/admin/hospitalManagement"/>">기타관리</a>
					<ul class="sub">
						<li><a href="<c:url value="/admin/hospitalManagement"/>">참여병원관리</a></li>
						<li><a href="<c:url value="/admin/prescripitionTemplateMain"/>">처방탬플릿관리</a></li>
					</ul>
				</li>
			</ul>
		 </div>	
 	</div>	
	<!-- gnbWrap :e -->
	
	<div id="globalWrap">
		<ul class="global">
			<li>안녕하세요. <c:out value="${loginUser.name }"/>님</li>
			<li><a href="<c:url value="/user/changeUser"/>"> 회원정보수정</a></li>
			<li><a href="<c:url value="/user/logout"/>"> 로그아웃</a></li>
		</ul>
	</div>
</div>