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
	
	function nextPage(obj, page){
		$("#nextPage").attr("action",$(obj).attr("href"));
		$("#page").val(page);
		$("#nextPage").submit();
	}
</script>
<div id="header">
	
	<h1 id="logo"><a href="<c:url value="/main/selectFunc"/>"><img src="<c:url value="/img/layout/logo.png"/>" alt="충북대학교 병원" /></a></h1>
	

	<!-- gnbWrap :s -->
	<div id="gnbWrap">	
		<div id="gnb">
			<ul class="menu">
				<li><a href="<c:url value="/prescription/main"/>">환자 처방</a>
					<ul class="sub">
						<li><a href="<c:url value="/prescription/main"/>">환자 처방</a></li>
						
					</ul>
				</li>
				<li><a href="<c:url value="/prescription/templateMain"/>">처방전 템플릿</a>
					<ul class="sub">
						<li><a href="<c:url value="/prescription/templateMain?template=me"/>" onclick="nextPage(this,1)" >나의 템플릿</a></li>
					</ul>
				</li>
				<!-- 
				<li><a href="<c:url value="/board/boardMain"/>">게시판</a>
					<ul class="sub">
						<li><a href="<c:url value="/prescription/main"/>">공지사항</a></li>
						<li><a href="<c:url value="/prescription/main"/>">자유게시판</a></li>
					</ul>
				</li> -->
			</ul>
		 </div>	
 	</div>	
	<!-- gnbWrap :e -->
	<form id="nextPage" method="POST">
		<input type="hidden" id="page" name="page"/>
	</form>
	
	<div id="globalWrap">
		<ul class="global">
			<li>안녕하세요. <c:out value="${loginUser.name }"/>님</li>
			<li><a href="<c:url value="/user/changeUser"/>"> 회원정보수정</a></li>
			<li><a href="<c:url value="/user/showLeavePage"/>">회원탈퇴</a></li>
			<li><a href="<c:url value="/user/logout"/>"> 로그아웃</a></li>
		</ul>
	</div>
	
</div>