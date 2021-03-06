<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/include.jsp" %>
<script>
	$(document).ready(function(){
		<c:set var="loginUser" value="${user }"/>
			<c:if test="${loginUser==null }">
				alert("로그인이 필요한 페이지 입니다.");
			</c:if>
	});
</script>
<div id="header">
	<div id="globalWrap">
		<ul class="global">
			<li>안녕하세요. <c:out value="${loginUser.name }"/>님</li>
			<li><a href="<c:url value="/user/changeUser"/>"> 회원정보수정</a></li>
			<li><a href="<c:url value="/user/logout"/>"> 로그아웃</a></li>
		</ul>
	</div>


	<h1 id="logo"><a href="<c:url value="/main/selectFunc"/>"><img src="<c:url value="/img/layout/logo.png"/>" alt="충북대학교 병원" /></a></h1>
	

	<!-- gnbWrap :s -->
	<div id="gnbWrap">	
		<div id="gnb">
			<ul class="menu">
				<li><a href="#">대메뉴1</a>
					<ul class="sub">
						<li><a href="#">2차메뉴1</a></li>
						<li><a href="#">2차메뉴2</a></li>
						<li><a href="#">2차메뉴3</a></li>
					</ul>
				</li>
				<li><a href="#">대메뉴2</a>
					<ul class="sub">
						<li><a href="#">2차메뉴1</a></li>
						<li><a href="#">2차메뉴2</a></li>
						<li><a href="#">2차메뉴3</a></li>
					</ul>
				</li>
				<li><a href="#">대메뉴3</a>
					<ul class="sub">
						<li><a href="#">2차메뉴1</a></li>
						<li><a href="#">2차메뉴2</a></li>
						<li><a href="#">2차메뉴3</a></li>
						
					</ul>
				</li>
			</ul>
		 </div>	
 	</div>	
	<!-- gnbWrap :e -->
</div>